import SockJS from 'sockjs-client'
import { Client, IMessage } from '@stomp/stompjs'
import {ChatMessageDto} from '@/api/models/ChatMessageDto'

let stompClient: Client | null = null

export function connectToGroup(
  groupId: number,
  onMessage: (message: ChatMessageDto) => void
): void {
  const socket = new SockJS('http://localhost:8080/ws-chat')

  stompClient = new Client({
    webSocketFactory: () => socket,
    onConnect: () => {
      stompClient?.subscribe(`/topic/chat.${groupId}`, (msg: IMessage) => {
        const message: ChatMessageDto = JSON.parse(msg.body)
        onMessage(message)
      })
    }
  })

  stompClient.activate()
}

export function sendMessageToGroup(groupId: number, message: ChatMessageDto): void {
  if (stompClient?.connected) {
    stompClient.publish({
      destination: `/app/chat.send/${groupId}`,
      body: JSON.stringify(message)
    })
  }
}
