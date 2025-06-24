package pbs.agile.webapi.services

import org.springframework.stereotype.Service
import pbs.agile.webapi.models.entities.ErrorLog
import pbs.agile.webapi.repositories.ErrorLogRepository
import java.time.LocalDateTime

@Service
class ErrorLogService(
    private val errorLogRepository : ErrorLogRepository
) {

    fun logException(e: Throwable) {
        val errorLog = ErrorLog(
            timestamp = LocalDateTime.now(),
            errorType = e::class.simpleName ?: "Unknown Exception",
            message = sanitizeMessage(e.message),
            stackTrace = e.stackTraceToString(),
            cause = e.cause?.toString()
        )
        errorLogRepository.save(errorLog)
    }

    private fun sanitizeMessage(message: String?): String {
        if (message == null) return "No error message"

        return message
            .replace(Regex("(?i)password=[^&\\s]+"), "password=***")
            .replace(Regex("(?i)token=[^&\\s]+"), "token=***")
            .replace(Regex("(?i)secret=[^&\\s]+"), "secret=***")
    }
}