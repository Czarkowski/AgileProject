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