<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { connectToGroup, sendMessageToGroup } from '@/services/stomp-service'
import { ChatMessageDto } from '@/api/models/ChatMessageDto'

const route = useRoute()
const projectId = Number(route.params.projectId)

const messages = ref<ChatMessageDto[]>([])
const input = ref('')

onMounted(() => {
  connectToGroup(projectId, (msg: ChatMessageDto) => {
    messages.value.push(msg)
  })
})

function send() {
  const msg: ChatMessageDto = {
    senderId: 2, // lub dynamicznie z auth
    content: input.value,
    timestamp: new Date().toISOString(),
  }
  sendMessageToGroup(projectId, msg)
  input.value = ''
}
</script>

<template>
  <div>
    <div v-for="m in messages" :key="m.timestamp">
      {{ m.senderId }}: {{ m.content }}
    </div>
    <input v-model="input" @keyup.enter="send" placeholder="Wpisz wiadomość..." />
  </div>
</template>
