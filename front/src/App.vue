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
export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    handleLogin() {
      console.log("Email:", this.email, "Hasło:", this.password);
      // Tutaj można dodać obsługę logowania np. wysyłanie żądania do backendu
    }
  }
};
</script>

<style>
body {
  font-family: Arial, sans-serif;
}
</style>