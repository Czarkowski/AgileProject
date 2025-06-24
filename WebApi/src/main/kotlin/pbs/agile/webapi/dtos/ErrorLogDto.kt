package pbs.agile.webapi.dtos

import java.time.LocalDateTime

data class ErrorLogDto(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val errorType: String,
    val message: String,
    val stackTrace: String,
    val cause: String? = null
)