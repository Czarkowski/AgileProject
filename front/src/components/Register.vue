<template>
  <div class="register-container">
    <div class="register-box">
      <h2 class="title">Zarejestruj się</h2>
      <form @submit.prevent="handleRegister">
        <div class="input-group">
          <label for="username">Nazwa użytkownika</label>
          <input v-model="form.username" type="text" id="username" required />
        </div>
        <div class="input-group">
          <label for="email">Email</label>
          <input v-model="form.email" type="email" id="email" required />
        </div>
        <div class="input-group">
          <label for="password">Hasło</label>
          <input v-model="form.password" type="password" id="password" required />
        </div>
        <div class="input-group">
          <label for="confirmPassword">Potwierdź hasło</label>
          <input v-model="form.confirmPassword" type="password" id="confirmPassword" required />
        </div>
        <div class="input-group">
          <label for="first_name">Imię</label>
          <input v-model="form.firstName" type="text" id="first_name" required />
        </div>
        <div class="input-group">
          <label for="last_name">Nazwisko</label>
          <input v-model="form.lastName" type="text" id="last_name" required />
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

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { RegisterRequestBody } from '@/api/models/RegisterRequestBody';
import { AuthControllerApi } from '@/api/apis/AuthControllerApi';

const router = useRouter();

const form = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  firstName: '',
  lastName: ''
});

async function handleRegister() {
  if (form.value.password !== form.value.confirmPassword) {
    alert('Hasła nie pasują do siebie.');
    return;
  }

  const registerData: RegisterRequestBody = {
    username: form.value.username,
    password: form.value.password,
    email: form.value.email,
    firstName: form.value.firstName,
    lastName: form.value.lastName,
  };
  try {
    const api = new AuthControllerApi()
    const response = await api.register( { registerRequestBody: registerData } );
    // const response = await fetch('/auth/register', {
    //   method: 'POST',
    //   headers: { 'Content-Type': 'application/json' },
    //   body: JSON.stringify(RegisterRequestToJSON(registerData)),
    // });

    // if (!response.ok) {
    //   throw new Error(`Błąd: ${response.statusText}`);
    // }

    console.log('Rejestracja zakończona sukcesem!');
    router.push('/');
  } catch (error) {
    console.error('Błąd rejestracji:', error);
    alert('Nie udało się zarejestrować użytkownika.');
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea, #764ba2);
}

.register-box {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

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