package pbs.agile.webapi.requests


data class ProjectRequest(val ownerId: Long, val title: String, val description: String)
data class UserAndProjectRequest(val userId: Long, val projectId: Long)
data class ProjectUpdateRequest(
    val title: String,
    val description: String
)