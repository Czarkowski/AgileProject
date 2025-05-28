<script setup lang="ts">
import Chat from '@/components/ProjectChat.vue'
</script>

<template>
  <div class="project-details">
    <h2 class="title">Szczegóły projektu</h2>
    <Chat />
  </div>
</template>

<style scoped>
.project-details {
  padding: 2rem;
}

.title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
</style>