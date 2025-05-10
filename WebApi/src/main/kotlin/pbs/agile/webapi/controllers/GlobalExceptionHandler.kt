package pbs.agile.webapi.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import pbs.agile.webapi.services.ErrorLogService

@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler(
    private val logErrorService: ErrorLogService
) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllException(e: Exception): Map<String, String> {
        logErrorService.logException(e)

        return mapOf(
            "error" to (e::class.simpleName ?: "Exception"),
            "message" to (e.message ?: "Unexpected error occurred")
        )
    }


}