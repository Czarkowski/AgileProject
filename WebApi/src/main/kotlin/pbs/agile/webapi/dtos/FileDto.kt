package pbs.agile.webapi.dtos

data class FileDto(
    val id: Long,
    val filename: String,
    val filepath: String,
    val fileType: String,
    val fileSize: Long,
    val uploadDate: String,
    val uploader: UserSimpleDto
)