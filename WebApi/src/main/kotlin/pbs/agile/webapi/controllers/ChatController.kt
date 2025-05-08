package pbs.agile.webapi.controllers

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.dtos.ChatMessageDto
import pbs.agile.webapi.repositories.ChatMessageRepository
import pbs.agile.webapi.services.ChatMessageService
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/chats")
class ChatController(
    private val chatMessageService: ChatMessageService,
) {

    @GetMapping("/project-messages/{projectId}")
    fun sendToGroup(
        @PathVariable projectId: Long,
        @RequestParam dateFrom: LocalDateTime,
        @RequestParam dateTo: LocalDateTime,
    ): List<ChatMessageDto> {
        return chatMessageService.getMessagesForProject(projectId, dateFrom, dateTo)
    }
}
