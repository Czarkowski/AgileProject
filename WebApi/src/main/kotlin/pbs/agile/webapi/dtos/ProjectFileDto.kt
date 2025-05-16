package pbs.agile.webapi.dtos

data class ProjectFileDto(
    val id: Long,
    val title: String,
    val description: String,
    val owner: UserSimpleDto?
)

data class UserSimpleDto(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)