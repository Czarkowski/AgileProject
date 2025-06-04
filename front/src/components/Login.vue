<template>
  <div class="flex items-center justify-center h-screen bg-gradient-to-r from-purple-500 to-pink-500">
    <div class="bg-white p-8 rounded-xl shadow-xl w-96 max-w-md">
      <h2 class="text-3xl font-bold text-center mb-6 text-gray-800">Logowanie</h2>
      <form @submit.prevent="handleLogin">
        <div class="mb-6">
          <label for="email" class="block text-gray-700 text-sm font-medium">Email</label>
          <input v-model="email" type="email" id="email" class="w-full p-3 border border-gray-300 rounded-md mt-2 focus:outline-none focus:ring-2 focus:ring-purple-500" required />
        </div>
        <div class="mb-6">
          <label for="password" class="block text-gray-700 text-sm font-medium">Hasło</label>
          <input v-model="password" type="password" id="password" class="w-full p-3 border border-gray-300 rounded-md mt-2 focus:outline-none focus:ring-2 focus:ring-purple-500" required />
        </div>
        <button type="submit" class="w-full bg-purple-600 text-white py-3 rounded-md hover:bg-purple-700 transition duration-300 transform hover:scale-105">
          Zaloguj się
        </button>
        <div class="text-center mt-4">
          <span class="text-sm text-gray-600">Nie masz konta? </span>
          <a href="#" @click="goToRegister" class="text-purple-600 hover:underline">Zarejestruj się</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { LoginRequestBody } from '@/api/models/LoginRequestBody';
import { UserControllerApi } from '@/api/apis/UserControllerApi';
import { AuthControllerApi } from '@/api/apis/AuthControllerApi';

export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        const loginRequest: LoginRequestBody = {
          identifier: this.email,
          password: this.password,
        };

        const authControllerApi = new AuthControllerApi();
        const response = await authControllerApi.login( { loginRequestBody: loginRequest } )

        // const userApi = new UserControllerApi();
        // const userResponse = await userApi.getUserByUsername({ username: this.email });


        if (response) {
          console.log(response)
          // console.log('Zalogowano pomyślnie:', userResponse);
          localStorage.setItem('loggedUser', JSON.stringify(response));

          this.$router.push('/projects');
        }
      } catch (error) {
        console.error('Błąd logowania:', error);
        alert('Nie udało się zalogować. Sprawdź swoje dane logowania.');
      }
    },

    goToRegister() {
      this.$router.push('/register');
    }
  }
};
</script>

<style scoped>
/* Stylowanie głównego kontenera */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea, #764ba2);
}

/* Stylizacja formularza */
.login-box {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

/* Nagłówek */
.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

/* Pola formularza */
.input-group {
  margin-bottom: 15px;
  text-align: left;
}

.input-group label {
  font-size: 14px;
  color: #555;
  display: block;
  margin-bottom: 5px;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

/* Przycisk logowania */
.login-button {
  width: 100%;
  padding: 10px;
  background-color: #764ba2;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.login-button:hover {
  background-color: #5a3e91;
}

/* Przekierowanie do rejestracji */
.redirect-text {
  margin-top: 15px;
  font-size: 14px;
  color: #555;
}

.register-link {
  color: #764ba2;
  font-weight: bold;
  text-decoration: none;
}

.register-link:hover {
  text-decoration: underline;
}
</style>