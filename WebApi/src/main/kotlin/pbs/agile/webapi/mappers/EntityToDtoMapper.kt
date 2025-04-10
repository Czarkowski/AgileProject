package pbs.agile.webapi.mappers

import pbs.agile.webapi.dtos.LoggedUserDto
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User

//fun <T> Collection<T>.toDTOList(): List<Any> {
//    return this.stream().map {
//        val toDtoMethod = it!!::class.memberFunctions.find { member -> member.name == "toDTO" }
//        toDtoMethod?.call(it) // Wywołaj metodę toDTO
//    }.filterNotNull() // Usuń wartości null, jeśli metoda toDTO nie istniała
//}


fun Project.toDTO(): ProjectDto {
    return ProjectDto(
        id = this.id ?: 0L,
        title = this.title,
        description = this.description
    )
}

fun User.toDTO(): UserDto {
    return UserDto(
        id = this.id ?: 0L,
        username = this.username,
        email = this.email,
        last_name = this.last_name,
        first_name = this.first_name
    )
}

fun User.toLoggedUserDTO(): LoggedUserDto {
    return LoggedUserDto(
            id = this.id ?: 0L,
            username = this.username,
            email = this.email,
            last_name = this.last_name,
            first_name = this.first_name
    )
}