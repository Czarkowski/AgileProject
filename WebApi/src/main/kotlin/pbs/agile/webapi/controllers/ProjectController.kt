package pbs.agile.webapi.controllers

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.requests.UserAndProjectRequestBody
import pbs.agile.webapi.requests.ProjectAddRequestBody
import pbs.agile.webapi.requests.ProjectUpdateRequestBody
import pbs.agile.webapi.services.ProjectService

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = ["http://localhost:5173"])
class ProjectController(@Autowired private val projectService: ProjectService) {

    @GetMapping
    fun getAllProjects(): List<ProjectDto> = projectService.getAllProjects()

    @PostMapping
    fun addProject(@RequestBody project: ProjectAddRequestBody): ProjectDto {

        return projectService.addProject(project)
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
    fun addUserToProject(@RequestBody userAddRequest: UserAndProjectRequestBody
        ): ResponseEntity<Any> {
        return try {
            val isOk = projectService.addUserToProject(userAddRequest.projectId, userAddRequest.userId)
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(isOk)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @PostMapping("/removeUser")
    fun deleteUserFromProject(@RequestBody userAddRequest: UserAndProjectRequestBody
        ): ResponseEntity<Any> {
        return try {
            val isOk = projectService.deleteUserFromProject(userAddRequest.projectId, userAddRequest.userId)
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(isOk)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}
