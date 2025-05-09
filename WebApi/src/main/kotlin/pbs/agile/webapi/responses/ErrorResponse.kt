package pbs.agile.webapi.responses

data class ErrorResponse(
    val error: String,
    val details: String?
)