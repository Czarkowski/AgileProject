package pbs.agile.webapi.dtos

data class ChatMessageDto(
        val senderId: Long,
        val content: String,
        val timestamp: String
)
