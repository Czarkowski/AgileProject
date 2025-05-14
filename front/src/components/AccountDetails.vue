<template>
  <div class="container">
    <div class="title">Edycja konta</div>

    <div v-if="isEditingUserData" class="edit-account">
      <label for="firstName">Imię</label>
      <input id="firstName" class="input-field" v-model="firstName" maxlength="50" />

      <label for="lastName">Nazwisko</label>
      <input id="lastName" class="input-field" v-model="lastName" maxlength="50" />

      <div v-if="showError" class="error">{{ errorMessage }}</div>

      <button @click="updateAccount()">Zapisz zmiany</button>
      <button @click="togglePasswordChange">Zmień hasło</button>
    </div>

    <div v-if="!isEditingUserData" class="edit-password">
      <label for="currentPassword">Aktualne hasło</label>
      <input
        type="password"
        id="currentPassword"
        class="input-field"
        v-model="currentPassword"
        maxlength="50"
      />

      <label for="newPassword">Nowe hasło</label>
      <input
        type="password"
        id="newPassword"
        class="input-field"
        v-model="newPassword"
        maxlength="50"
      />

      <label for="confirmPassword">Potwierdź nowe hasło</label>
      <input
        type="password"
        id="confirmPassword"
        class="input-field"
        v-model="confirmPassword"
        maxlength="50"
      />

      <div v-if="showError" class="error">{{ errorMessage }}</div>

      <button @click="updatePassword()">Zmień hasło</button>
      <button @click="togglePasswordChange">Powrót</button>
    </div>

    <div class="links back" @click="goBack">Powrót</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { UserControllerApi, AuthControllerApi } from '@/api/apis';

export default defineComponent({
  name: 'AccountEdit',
  data() {
    return {
      firstName: '',
      lastName: '',
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
      showError: false,
      errorMessage: 'Imię i nazwisko są wymagane.',
      isEditingUserData: true,
    };
  },

  mounted() {
    this.loadUserData();
  },

  methods: {
    async loadUserData() {
      try {
        const token = localStorage.getItem("token");
        if (!token) throw new Error("Brakuje tokena.");

        const payload = this.parseJwt(token);
        const username = payload?.sub;

        if (!username) throw new Error("Nie udało się odczytać użytkownika z tokena.");

        const userApi = new UserControllerApi();
        const user = await userApi.getUserByUsername({ username }, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        this.firstName = user.firstName || '';
        this.lastName = user.lastName || '';
      } catch (error) {
        console.error("Błąd ładowania danych użytkownika:", error);
        this.errorMessage = "Nie udało się załadować danych użytkownika.";
        this.showError = true;
      }
    },

    async updateAccount() {
      const token = localStorage.getItem("token");

      if (!token) {
        this.errorMessage = "Brakuje tokena.";
        this.showError = true;
        return;
      }

      const apiUser = new UserControllerApi();

      const updateData = {
        userUpdateRequestBody: {
          firstName: this.firstName,
          lastName: this.lastName
        }
      };

      try {
        await apiUser.updateUser(updateData, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        alert("Zaktualizowano dane użytkownika.");
        this.showError = false;
      } catch (error: any) {
        console.error("Błąd aktualizacji:", error);
        this.errorMessage = "Wystąpił błąd podczas aktualizacji.";
        this.showError = true;
      }
    },

    togglePasswordChange() {
      this.isEditingUserData = !this.isEditingUserData;
      this.currentPassword = '';
      this.newPassword = '';
      this.confirmPassword = '';
    },

    parseJwt(token: string) {
      try {
        return JSON.parse(atob(token.split('.')[1]));
      } catch (e) {
        console.error("Nie udało się zdekodować tokena", e);
        return null;
      }
    },

    async updatePassword() {
      if (this.newPassword !== this.confirmPassword) {
        this.errorMessage = "Nowe hasło i potwierdzenie nie są zgodne.";
        this.showError = true;
        return;
      }

      const token = localStorage.getItem("token");
      if (!token) {
        this.errorMessage = "Brakuje tokena.";
        this.showError = true;
        return;
      }

      const payload = this.parseJwt(token);
      const username = payload?.sub;

      if (!username) {
        this.errorMessage = "Nie udało się odczytać nazwy użytkownika z tokena.";
        this.showError = true;
        return;
      }

      const authApi = new AuthControllerApi();

      try {
        const changePasswordRequestBody = {
          identifier: username,
          oldPassword: this.currentPassword,
          newPassword: this.newPassword,
        };

        await authApi.changePassword(
          { changePasswordRequestBody },
          {
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          }
        );

        alert("Hasło zostało zmienione.");
        this.togglePasswordChange();
        this.showError = false;
      } catch (error: any) {
        console.error("Błąd zmiany hasła:", error);
        this.errorMessage = "Wystąpił błąd podczas zmiany hasła.";
        this.showError = true;
      }
    },

    goBack() {
      this.$router.push('/projects/');
    }
  }
});
</script>

<style scoped>
.edit-account, .edit-password {
  width: 100vmin;
  display: flex;
  flex-direction: column;
  gap: 2vmin;
  background-color: #2c3e50;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin: 20px auto;
  padding: 20px;
}

.title {
  text-align: center;
  font-size: 24px;
}

.input-field {
  width: 100%;
}

.links.back {
  margin-left: 0;
  margin-right: auto;
}

.error {
  color: darkred;
}
</style>
