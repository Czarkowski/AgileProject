<template>
  <div class="flex items-center justify-center h-screen bg-gray-100">
    <div class="bg-white p-8 rounded-lg shadow-lg w-96">
      <h2 class="text-2xl font-bold text-center mb-6">Logowanie</h2>
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label for="email" class="block text-gray-700">Email</label>
          <input v-model="email" type="email" id="email" class="w-full p-2 border rounded mt-1" required />
        </div>
        <div class="mb-4">
          <label for="password" class="block text-gray-700">Hasło</label>
          <input v-model="password" type="password" id="password" class="w-full p-2 border rounded mt-1" required />
        </div>
        <button type="submit" class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition">
          Zaloguj się
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { AuthControllerApi } from '/src/api';  // Import wygenerowanego klienta API, dostosuj ścieżkę
export default {
  
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async handleLogin() {
      console.log("Email:", this.email, "Hasło:", this.password);
      // Tutaj można dodać obsługę logowania np. wysyłanie żądania do backendu
      try {
        // Tworzymy instancję klienta API
        const api = new AuthControllerApi();
        console.log(api)
        // Wywołujemy metodę logowania, przekazując email i password
        const response = await api.login({loginRequest: { identifier: this.email, password: this.password }});

        // Jeśli logowanie się uda, przechowujemy token JWT w localStorage
        localStorage.setItem('accessToken', response.data.accessToken);

        // Tutaj możesz zrobić przekierowanie do innej strony po udanym logowaniu
        this.$router.push('/dashboard');  // Przykład przekierowania do dashboardu
      } catch (error) {
        console.error('Błąd logowania:', error);
        // Obsłuż błąd logowania (np. wyświetl komunikat użytkownikowi)
      }
    }
  }
};
</script>

<style>
body {
  font-family: Arial, sans-serif;
}
</style>