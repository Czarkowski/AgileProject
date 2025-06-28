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
import java.time.OffsetDateTime

@RestController
@RequestMapping("/api/chats")
class ChatController(
    private val chatMessageService: ChatMessageService,
) {

    @GetMapping("/project-messages/{projectId}")
    fun getHistory(
        @PathVariable projectId: Long,
        @RequestParam dateFrom: OffsetDateTime,
        @RequestParam dateTo: OffsetDateTime,
    ): List<ChatMessageDto> {
        return chatMessageService.getMessagesForProject(projectId, dateFrom.toLocalDateTime(), dateTo.toLocalDateTime())
    }
}
