package pbs.agile.webapi.controllers

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import pbs.agile.webapi.dtos.ChatMessageDto
import pbs.agile.webapi.services.ChatMessageService

@Controller
class ChatWebSocketController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatMessageService: ChatMessageService,
    ) {
    @MessageMapping("/chat.send/{projectId}")
    fun sendToGroup(
        @DestinationVariable projectId: Long,
        messageDto: ChatMessageDto
    ) {
        val savedMessageDto = chatMessageService.saveChatMessage(messageDto, projectId)

        // tutaj możesz np. zapisać wiadomość do bazy
        messagingTemplate.convertAndSend("/topic/chat.$projectId", savedMessageDto)
    }
}
