package pbs.agile.webapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.mappers.toDTO
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.repositories.ProjectRepository
import pbs.agile.webapi.requests.ProjectRequest

@Service
class ProjectService(@Autowired private val projectRepository: ProjectRepository) {

    fun getAllProjects(): List<ProjectDto> {
        var projects: List<Project> = projectRepository.findAll();
        var res = projects.stream().map { it.toDTO() } .toList();
        return res;
    }

    fun addProject(projectRequest: ProjectRequest): ProjectDto{
        var project: Project = Project()
        project.description = projectRequest.description
        project.title = projectRequest.title
        project = projectRepository.save(project)
        return project.toDTO()
    }
}



