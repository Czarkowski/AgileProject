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
            message = e.message ?: "No error message",
            stackTrace = e.stackTraceToString(),
            cause = e.cause?.toString()
        )
        errorLogRepository.save(errorLog)
    }
}