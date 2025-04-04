package pbs.agile.webapi.services

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pbs.agile.webapi.dtos.ProjectDto
import pbs.agile.webapi.mappers.toDTO
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.repositories.ProjectRepository
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.requests.ProjectRequest
import pbs.agile.webapi.requests.ProjectUpdateRequest

@Service
class ProjectService(@Autowired private val projectRepository: ProjectRepository,
                     @Autowired private val userRepository: UserRepository) {

    fun getAllProjects(): List<ProjectDto> {
        var projects: List<Project> = projectRepository.findAll();
        var res = projects.stream().map { it.toDTO() } .toList();
        return res;
    }

    fun addProject(projectRequest: ProjectRequest): ProjectDto{
        val owner = userRepository.findById(projectRequest.ownerId).orElseThrow()
        var project: Project = Project()
        project.description = projectRequest.description
        project.title = projectRequest.title
        project = projectRepository.save(project)
        project.owner = owner
        return project.toDTO()
    }

    @Transactional
    fun addUserToProject(projectId: Long, userId: Long): Boolean {
        val project = projectRepository.findById(projectId)
            .orElseThrow { throw EntityNotFoundException("Project not found with id: $projectId") }

        val user = userRepository.findById(userId)
            .orElseThrow { throw EntityNotFoundException("User not found with id: $userId") }

        // Sprawdzamy, czy użytkownik już jest w projekcie
        if (!project.users.contains(user)) {
            project.users.add(user)  // Dodanie użytkownika do projektu
            projectRepository.save(project)  // Zapisujemy zmiany w bazie
        }
        else{
            throw EntityExistsException("User already found with id: $userId in project with id: $projectId")
        }
        return true
    }

    @Transactional
    fun deleteUserFromProject(projectId: Long, userId: Long): Boolean {
        val project = projectRepository.findById(projectId)
            .orElseThrow { throw EntityNotFoundException("Project not found with id: $projectId") }

        val user = userRepository.findById(userId)
            .orElseThrow { throw EntityNotFoundException("User not found with id: $userId") }

        // Sprawdzamy, czy użytkownik już jest w projekcie
        if (project.users.contains(user)) {
            project.users.remove(user)  // Usunięcie użytkownika z projektu
            projectRepository.save(project)  // Zapisujemy zmiany w bazie
        }
        else{
            throw EntityNotFoundException("User not found with id: $userId in project with id: $projectId")
        }
        return true
    }

    @Transactional
    fun updateProject(projectId: Long, updateRequest: ProjectUpdateRequest) {
        val project = projectRepository.findById(projectId)
            .orElseThrow { EntityNotFoundException("Project with ID $projectId not found") }

        project.title = updateRequest.title
        project.description = updateRequest.description

        projectRepository.save(project)  // Hibernate automatycznie zapisze zmiany dzięki @Transactional
    }
}



