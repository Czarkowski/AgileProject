package pbs.agile.webapi.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.requests.ProjectRequest
import pbs.agile.webapi.services.ProjectService
import pbs.agile.webapi.services.UserService

@RestController
@RequestMapping("/api/projects")
class ProjectController(@Autowired private val projectService: ProjectService) {

    @GetMapping
    fun getAllProjects(): List<ProjectDto> = projectService.getAllProjects()

    @PostMapping
    fun addProject(@RequestBody project: ProjectRequest): ProjectDto {

        return projectService.addProject(project)
    }
}
