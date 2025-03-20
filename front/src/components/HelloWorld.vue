<script setup>

import { ref, onMounted } from 'vue';
import { UsersApi } from '@/api';  // Dostosuj ścieżkę do wygenerowanej klasy API
import type { UserDto } from '@/api'; // Importuj typy wygenerowane przez OpenAPI, jeśli są dostępne

// Tworzymy zmienną do przechowywania danych użytkowników
const users = ref<UserDto[]>([]);

// Wywołanie metody API po załadowaniu komponentu
onMounted(async () => {
  try {
    const api = new UsersApi(); // Inicjalizacja klienta API
    const response = await api.getAllUsers(); // Wywołanie metody getAllUsers
    users.value = response.data; // Zakładając, że odpowiedź zawiera dane użytkowników
  } catch (error) {
    console.error('Error fetching users:', error);
  }
});

defineProps({
  msg: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      You’ve successfully created a project with
      <a href="https://vite.dev/" target="_blank" rel="noopener">Vite</a> +
      <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue 3</a>.
    </h3>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
