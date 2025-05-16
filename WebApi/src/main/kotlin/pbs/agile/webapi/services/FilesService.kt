package pbs.agile.webapi.services

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import pbs.agile.webapi.models.entities.Files
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.FilesRepository
import java.io.File
import java.nio.file.Files as NioFiles
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*

@Service
class FilesService(
    private val filesRepository: FilesRepository
) {
    private val uploadDir = "uploads"

    init {
        val dir = File(uploadDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    fun saveFile(
        file: MultipartFile,
        project: Project,
        uploader: User
    ): Files {
        val allowedTypes = listOf(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "text/plain",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        )
        require(file.contentType in allowedTypes) { "Niedozwolony typ pliku." }
        require(file.size <= 5 * 1024 * 1024) { "Plik za duży (max 5MB)." }

        val extension = file.originalFilename?.substringAfterLast('.', "") ?: ""
        val uniqueName = "${UUID.randomUUID()}.$extension"
        val filePath = Paths.get(uploadDir, uniqueName)

        file.inputStream.use { input ->
            NioFiles.copy(input, filePath)
        }

        val filesEntity = Files(
            project = project,
            uploader = uploader,
            filename = file.originalFilename ?: uniqueName,
            filepath = filePath.toString(),
            fileType = file.contentType ?: "application/octet-stream",
            fileSize = file.size,
            uploadDate = LocalDateTime.now()
        )
        return filesRepository.save(filesEntity)
    }

    fun getFilesByProject(projectId: Long): List<Files> =
        filesRepository.findAllByProject_Id(projectId)

    fun getFileById(fileId: Long): Files =
        filesRepository.findById(fileId).orElseThrow { NoSuchElementException("Plik nie istnieje") }

    fun deleteFile(fileId: Long) {
        val file = getFileById(fileId)
        val path = Paths.get(file.filepath)
        NioFiles.deleteIfExists(path)
        filesRepository.delete(file)
    }
}