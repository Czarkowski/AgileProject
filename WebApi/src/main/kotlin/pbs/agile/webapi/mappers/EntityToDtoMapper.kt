package pbs.agile.webapi.mappers

import pbs.agile.webapi.dtos.ChatMessageDto
import pbs.agile.webapi.dtos.ErrorLogDto
import pbs.agile.webapi.dtos.LoggedUserDto
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.models.entities.ChatMessage
import pbs.agile.webapi.models.entities.ErrorLog
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User

//fun <T> Collection<T>.toDTOList(): List<Any> {
//    return this.stream().map {
//        val toDtoMethod = it!!::class.memberFunctions.find { member -> member.name == "toDTO" }
//        toDtoMethod?.call(it) // Wywołaj metodę toDTO
//    }.filterNotNull() // Usuń wartości null, jeśli metoda toDTO nie istniała
//}

//inline fun <reified T : Any, reified R> Collection<T>.mapToDto(): List<R> {
//    val mapper = T::class.members
//        .firstOrNull { it.name == "toDTO" }
//    return this.mapNotNull { item ->
//        mapper!!.call(item) as? R
//    }
//}


fun Project.toDTO(): ProjectDto {
    return ProjectDto(
        id = this.id ?: 0L,
        title = this.title,
        description = this.description,
            ownerId =  this.owner!!.id!!,
            members = this.users.map{ it.toDTO() },
            completionDate = this.completionDate
    )
}

fun User.toDTO(): UserDto {
    return UserDto(
        id = this.id ?: 0L,
        username = this.username,
        email = this.email,
        last_name = this.lastName,
        first_name = this.firstName
    )
}

fun User.toLoggedUserDTO(): LoggedUserDto {
    return LoggedUserDto(
            id = this.id ?: 0L,
            username = this.username,
            email = this.email,
            last_name = this.lastName,
            first_name = this.firstName
    )
}

fun ChatMessage.toDTO(): ChatMessageDto {
    return ChatMessageDto(
        senderId = this.sender.id!!,
        content = this.content,
        timestamp = this.timestamp,
    )
}

fun ErrorLog.toDTO(): ErrorLogDto {
    return ErrorLogDto(
        timestamp = this.timestamp,
        errorType = this.errorType,
        message = this.message,
        stackTrace = this.stackTrace,
    )
}