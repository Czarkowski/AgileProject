package pbs.agile.webapi.dtos

data class LoggedUserDto(
        val id: Long,
        val username: String,
        val email: String,
        val first_name: String,
        val last_name: String
)