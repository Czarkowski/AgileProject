package pbs.agile.webapi.mappers

import pbs.agile.webapi.dtos.ProjectFileDto
import pbs.agile.webapi.dtos.UserSimpleDto
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User

fun Project.toFileDto(): ProjectFileDto = ProjectFileDto(
    id = this.id ?: 0L,
    title = this.title,
    description = this.description,
    owner = this.owner?.toSimpleDto()
)

fun User.toSimpleDto(): UserSimpleDto = UserSimpleDto(
    id = this.id ?: 0L,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName
)