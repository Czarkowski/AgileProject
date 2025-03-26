<template>
  <div class="register-container">
    <div class="register-box">
      <h2 class="title">Zarejestruj się</h2>
      <form @submit.prevent="handleRegister">
        <div class="input-group">
          <label for="username">Nazwa użytkownika</label>
          <input v-model="username" type="text" id="username" required />
        </div>
        <div class="input-group">
          <label for="email">Email</label>
          <input v-model="email" type="email" id="email" required />
        </div>
        <div class="input-group">
          <label for="password">Hasło</label>
          <input v-model="password" type="password" id="password" required />
        </div>
        <div class="input-group">
          <label for="confirmPassword">Potwierdź hasło</label>
          <input v-model="confirmPassword" type="password" id="confirmPassword" required />
        </div>
        <div class="input-group">
          <label for="first_name">Imię</label>
          <input v-model="first_name" type="text" id="first_name" required />
        </div>
        <div class="input-group">
          <label for="last_name">Nazwisko</label>
          <input v-model="last_name" type="text" id="last_name" required />
        </div>
        <button type="submit" class="register-button">Zarejestruj się</button>
      </form>
      <p class="redirect-text">
        Masz już konto? 
        <router-link to="/login" class="login-link">Zaloguj się</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      first_name: '',
      last_name: '',
    };
  },
  methods: {
    async handleRegister() {
      if (this.password !== this.confirmPassword) {
        alert('Hasła nie pasują do siebie.');
        return;
      }

      try {
        const response = await axios.post('/api/users/register', {
          username: this.username,
          email: this.email,
          password: this.password,
          first_name: this.first_name,
          last_name: this.last_name,
        });

        if (response && response.data) {
          console.log('Rejestracja zakończona sukcesem:', response.data);
          this.$router.push('/login');
        }
      } catch (error) {
        console.error('Błąd rejestracji:', error);
        alert('Nie udało się zarejestrować użytkownika.');
      }
    },
  },
};
</script>

<style scoped>
/* Stylowanie głównego kontenera */
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea, #764ba2);
}

/* Stylizacja formularza */
.register-box {
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

/* Przycisk rejestracji */
.register-button {
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

.register-button:hover {
  background-color: #5a3e91;
}

/* Przekierowanie do logowania */
.redirect-text {
  margin-top: 15px;
  font-size: 14px;
  color: #555;
}

.login-link {
  color: #764ba2;
  font-weight: bold;
  text-decoration: none;
}

.login-link:hover {
  text-decoration: underline;
}
</style>
