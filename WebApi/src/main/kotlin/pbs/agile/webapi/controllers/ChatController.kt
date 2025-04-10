package pbs.agile.webapi.controllers

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import pbs.agile.webapi.dtos.ChatMessageDto
import pbs.agile.webapi.models.entities.ChatMessage
import pbs.agile.webapi.repositories.ChatMessageRepository

@Controller
class ChatController(
        val messagingTemplate: SimpMessagingTemplate,
        val chatMessageRepository: ChatMessageRepository
) {
    @MessageMapping("/chat.send/{groupId}")
    fun sendToGroup(
            @DestinationVariable groupId: String,
            message: ChatMessageDto
    ) {
        val savedMessage = ChatMessage(
                sender = message.,
                content = message.content,
                timestamp = message.timestamp,
                project =
        )
        chatMessageRepository.save(savedMessage)


        // tutaj możesz np. zapisać wiadomość do bazy
        messagingTemplate.convertAndSend("/topic/chat.$groupId", message)
    }
}
