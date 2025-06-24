package pbs.agile.webapi.controllers

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.dtos.ProjectFileDto
import pbs.agile.webapi.mappers.toFileDto
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.requests.UserAndProjectRequestBody
import pbs.agile.webapi.requests.ProjectAddRequestBody
import pbs.agile.webapi.requests.ProjectUpdateRequestBody
import pbs.agile.webapi.services.ProjectService

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = ["http://localhost:5173"])
class ProjectController(
    @Autowired private val projectService: ProjectService,
    private val userRepository: UserRepository
) {

    @GetMapping
    fun getAllProjects(
        @RequestParam(required = false) ownerId: Long?,
        @RequestParam(required = false) memberId: Long?,
        @RequestParam(required = false) byMember: Boolean?,
        authentication: Authentication,
    ): List<ProjectDto> {
        val userDetails = userRepository.findByUsername(authentication.name)!!
        val resolvedOwnerId: Long?
        val resolvedMemberId: Long?

        if (ownerId == null && memberId == null) {
            when (byMember) {
                true -> {
                    resolvedOwnerId = null
                    resolvedMemberId = userDetails.id
                }
                false -> {
                    resolvedOwnerId = userDetails.id
                    resolvedMemberId = null
                }
                null -> {
                    resolvedOwnerId = userDetails.id
                    resolvedMemberId = userDetails.id
                }
            }
        } else {
            resolvedOwnerId = ownerId
            resolvedMemberId = memberId
        }
        return projectService.getAllProjects(resolvedOwnerId, resolvedMemberId)
    }

    @GetMapping("/{projectId}")
    fun getAllProject(
        @PathVariable projectId: Long,
        authentication: Authentication,
    ): ProjectDto {
        return projectService.getProject(projectId)
    }

    @PostMapping("/add")
    fun addProject(@RequestBody request: ProjectAddRequestBody): ResponseEntity<ProjectFileDto> {
        val projectFileDto = projectService.addProject(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(projectFileDto)
    }

    @PutMapping("/{projectId}")
    fun updateProject(
        @PathVariable projectId: Long,
        @RequestBody updateRequest: ProjectUpdateRequestBody
    ): ResponseEntity<Any> {
        return try {
            projectService.updateProject(projectId, updateRequest)
            ResponseEntity.noContent().build()
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found")
        }
    }

    @PostMapping("/addUser")
    fun addUserToProject(@RequestBody userAddRequest: UserAndProjectRequestBody): ResponseEntity<Any> {
        return try {
            val isOk = projectService.addUserToProject(userAddRequest.projectId, userAddRequest.userId)
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(isOk)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @PostMapping("/removeUser")
    fun deleteUserFromProject(@RequestBody userAddRequest: UserAndProjectRequestBody): ResponseEntity<Any> {
        return try {
            val isOk = projectService.deleteUserFromProject(userAddRequest.projectId, userAddRequest.userId)
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(isOk)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @PostMapping("/{projectId}/complete")
    fun completeProject(@PathVariable projectId: Long): ResponseEntity<ProjectDto> {
        return try {
            val updatedProjectDto = projectService.markProjectAsCompleted(projectId)
            ResponseEntity.ok(updatedProjectDto)
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @PostMapping("/{projectId}/uncomplete")
    fun uncompleteProject(@PathVariable projectId: Long): ResponseEntity<ProjectDto> {
        return try {
            val updatedProjectDto = projectService.markProjectAsUncompleted(projectId)
            ResponseEntity.ok(updatedProjectDto)
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }
}
