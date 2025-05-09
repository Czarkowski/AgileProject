package pbs.agile.webapi.dtos

import java.time.LocalDateTime

data class ChatMessageDto(
        val senderId: Long,
        val content: String,
        val timestamp: LocalDateTime,
)
