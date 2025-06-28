<script setup lang="ts">
import { ref, onMounted, nextTick, watchEffect } from 'vue'
import { defineProps } from 'vue'
import { useRoute } from 'vue-router'
import { connectToGroup, sendMessageToGroup } from '@/services/stomp-service'
import { ChatMessageDto } from '@/api/models/ChatMessageDto'
import { refreshTokenIfNeeded, getLoggedUser } from '@/user'
import { ChatControllerApi } from '@/api/apis/ChatControllerApi'
import { Configuration } from '@/api/runtime'

const route = useRoute()
const projectId = Number(route.params.projectId)

const messages = ref<ChatMessageDto[]>([])
const input = ref('')
const isOpen = ref(false)

const senderId = ref<number | null>(null)
const senderUsername = ref<string | null>(null)

const props = defineProps<{
  userList: Array<{ id: number; username: string }>
}>()

const messagesContainer = ref<HTMLElement | null>(null)

const loggedUser = getLoggedUser()
const configuration = new Configuration({ accessToken: loggedUser.token });

const chatControllerApi = new ChatControllerApi(configuration);


function scrollToBottom() {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

onMounted(async () => {
  await refreshTokenIfNeeded()

  const loggedUser = getLoggedUser()

  if (!loggedUser || !loggedUser.loggedUser?.id || !loggedUser.loggedUser?.username) {
    console.error('Brak zalogowanego użytkownika')
    return
  }

  senderId.value = loggedUser.loggedUser.id
  senderUsername.value = loggedUser.loggedUser.username

  const now = new Date()
  const weekAgo = new Date()
  weekAgo.setDate(now.getDate() - 7)

  function formatAsLocalDateTime(date: Date): string {
    const pad = (n: number) => n.toString().padStart(2, '0');
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}` +
          `T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
  }

  try {
    const dateFrom = formatAsLocalDateTime(weekAgo) as unknown as Date
    const dateTo = formatAsLocalDateTime(now) as unknown as Date

    console.log('Pobieram historię:', { projectId, dateFrom, dateTo })

    const history = await chatControllerApi.sendToGroup({
      projectId: projectId,
      dateFrom: dateFrom,
      dateTo: dateTo
    })
    messages.value = history
    console.log('historia: ', history)
  } catch (error) {
    console.error('Błąd przy pobieraniu historii:', error)
  }

  connectToGroup(projectId, async (msg: ChatMessageDto) => {
    messages.value.push(msg)
  })
})

watchEffect(() => {
  messages.value.length
  scrollToBottom()
})

watchEffect(() => {
  if (isOpen.value) {
    scrollToBottom()
  }
})

function send() {
  if (!input.value.trim() || senderId.value === null) return

  const msg: ChatMessageDto = {
    senderId: senderId.value,
    content: input.value,
    timestamp: new Date().toISOString(),
  }

  sendMessageToGroup(projectId, msg)
  input.value = ''
}
</script>

<template>
  <div class="chat-float">
    <button class="chat-toggle" @click="isOpen = !isOpen">
      💬
    </button>

    <div v-if="isOpen" class="chat-box">
      <div class="messages" ref="messagesContainer">
        <div
          v-for="m in messages"
          :key="m.timestamp"
          :class="['message-bubble', m.senderId === senderId ? 'mine' : 'theirs']"
        >
          <div class="sender">
            {{ props.userList.find(user => user.id === m.senderId)?.username || `Użytkownik ${m.senderId}` }}
          </div>
          <div class="content">{{ m.content }}</div>
        </div>
      </div>
      <div class="input-area">
        <input
          v-model="input"
          @keyup.enter="send"
          class="chat-input"
          placeholder="Wpisz wiadomość..."
        />
        <button @click="send" class="send-button">➤</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.chat-float {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 999;
}

.chat-toggle {
  background-color: #0d6efd;
  color: white;
  display: flex;
  justify-content: center;
  border: none;
  border-radius: 50%;
  width: 56px;
  height: 56px;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.chat-box {
  width: 300px;
  height: 400px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  margin-top: 10px;
}

.messages {
  flex-grow: 1;
  overflow-y: auto;
  padding: 0.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  scroll-behavior: smooth;
}

.message-bubble {
  padding: 0.5rem 1rem;
  border-radius: 1rem;
  max-width: 75%;
  word-break: break-word;
}

.mine {
  align-self: flex-end;
  background-color: #d1e7dd;
  color: #0f5132;
}

.theirs {
  align-self: flex-start;
  background-color: #f8d7da;
  color: #842029;
}

.sender {
  font-size: 0.75rem;
  opacity: 0.6;
}

.input-area {
  display: flex;
  border-top: 1px solid #ddd;
  padding: 0.5rem;
  gap: 0.5rem;
}

.chat-input {
  flex-grow: 1;
  padding: 0.5rem;
  border-radius: 0.5rem;
  border: 1px solid #ccc;
}

.send-button {
  background-color: #0d6efd;
  color: white;
  border: none;
  border-radius: 0.5rem;
  padding: 0.5rem 0.75rem;
  cursor: pointer;
}
</style>