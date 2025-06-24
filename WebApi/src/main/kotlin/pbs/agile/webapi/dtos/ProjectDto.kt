package pbs.agile.webapi.dtos;

import java.time.LocalDateTime

data class ProjectDto (
    val id: Long,
    val title: String,
    val description: String,
    val ownerId: Long,
        val members: List<UserDto>,
        val completionDate: LocalDateTime?,
)

