package pbs.agile.webapi.mappers

import pbs.agile.webapi.dtos.FileDto
import pbs.agile.webapi.dtos.UserSimpleDto
import pbs.agile.webapi.models.entities.Files

fun Files.toDto(): FileDto = FileDto(
    id = this.id ?: 0L,
    filename = this.filename,
    filepath = this.filepath,
    fileType = this.fileType,
    fileSize = this.fileSize,
    uploadDate = this.uploadDate.toString(),
    uploader = UserSimpleDto(
        id = this.uploader.id ?: 0L,
        username = this.uploader.username,
        email = this.uploader.email,
        firstName = this.uploader.firstName,
        lastName = this.uploader.lastName
    )
)