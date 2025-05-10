package pbs.agile.webapi.exceptions

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import pbs.agile.webapi.services.ErrorLogService

@Hidden
@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler(
    private val logErrorService: ErrorLogService
) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllException(e: Exception): ErrorResponse {
        logErrorService.logException(e)

        return ErrorResponse(e::class.simpleName ?: "Exception",e.message ?: "Unknown error")
    }
}

data class ErrorResponse(val error: String, val message: String)