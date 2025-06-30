package pbs.agile.webapi.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pbs.agile.webapi.dtos.FileDto
import pbs.agile.webapi.mappers.toDto
import pbs.agile.webapi.models.entities.Files
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.ProjectRepository
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.services.FilesService
import java.io.File
import java.nio.file.Files as NioFiles

@RestController
@RequestMapping("/api/projects/{projectId}/files")
class FilesController(
    private val filesService: FilesService,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository
) {

    @PostMapping
    fun uploadFile(
        @PathVariable projectId: Long,
        @RequestParam("file") file: MultipartFile,
        authentication: Authentication,
    ): ResponseEntity<FileDto> {
        val project: Project = projectRepository.findById(projectId)
            .orElseThrow { NoSuchElementException("Projekt nie istnieje") }
        val uploader: User = userRepository.findByUsername(authentication.name)
            ?: throw NoSuchElementException("Użytkownik nie istnieje")
        val savedFile = filesService.saveFile(file, project, uploader)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFile.toDto())
    }

    @GetMapping
    fun listFiles(@PathVariable projectId: Long): List<FileDto> =
        filesService.getFilesByProject(projectId).map { it.toDto() }

    @GetMapping("/{fileId}")
    fun downloadFile(
        @PathVariable projectId: Long,
        @PathVariable fileId: Long
    ): ResponseEntity<ByteArray> {
        val fileMeta = filesService.getFileById(fileId)
        if (fileMeta.project.id != projectId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
        val file = File(fileMeta.filepath)
        val fileBytes = NioFiles.readAllBytes(file.toPath())
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${fileMeta.filename}\"")
            .contentType(MediaType.parseMediaType(fileMeta.fileType))
            .body(fileBytes)
    }

    @DeleteMapping("/{fileId}")
    fun deleteFile(
        @PathVariable projectId: Long,
        @PathVariable fileId: Long
    ): ResponseEntity<Void> {
        val fileMeta = filesService.getFileById(fileId)
        if (fileMeta.project.id != projectId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
        filesService.deleteFile(fileId)
        return ResponseEntity.noContent().build()
    }
}