package pbs.agile.webapi.requests


data class ProjectAddRequestBody(val ownerId: Long, val title: String, val description: String)
data class UserAndProjectRequestBody(val userId: Long, val projectId: Long)
data class ProjectUpdateRequestBody(
    val title: String,
    val description: String
)
data class LoginRequest(
    val usernameOrEmail: String,
    val password: String
)

data class RegisterRequestBody(val username: String, val password: String, val email: String, val first_name: String, val last_name: String)
data class LoginRequestBody(val identifier: String, val password: String)
data class RefreshTokenRequestBody(val refreshToken: String)