package pbs.agile.webapi.servicesTest

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.ProjectRepository
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.requests.ProjectAddRequestBody
import pbs.agile.webapi.requests.ProjectUpdateRequestBody
import pbs.agile.webapi.services.ProjectService
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProjectServiceTest {

    @Mock
    private lateinit var projectRepository: ProjectRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var projectService: ProjectService

    @Test
    fun `should return all projects`() {
        val project = Project(id = 1L, title = "Test Project", description = "Test Description", owner = null, users = mutableListOf())
        `when`(projectRepository.findAll()).thenReturn(listOf(project))

        val projects = projectService.getAllProjects(memberId = 1)

        assertEquals(1, projects.size)
        assertEquals("Test Project", projects[0].title)
    }

    @Test
    fun `should add project`() {
        val user = User(id = 1L, username = "testuser", password = "pass", email = "test@example.com", firstName = "Test", lastName = "User")
        val projectRequest = ProjectAddRequestBody(ownerId = 1L, title = "New Project", description = "New Description")
        val project = Project(id = 1L, title = "New Project", description = "New Description", owner = user, users = mutableListOf())

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        `when`(projectRepository.save(any(Project::class.java))).thenReturn(project)

        val result = projectService.addProject(projectRequest)

        assertNotNull(result)
        assertEquals("New Project", result.title)
    }

    @Test
    fun `should add user to project`() {
        val user = User(id = 2L, username = "testuser2", password = "pass", email = "test2@example.com", firstName = "Test", lastName = "User")
        val project = Project(id = 1L, title = "Project", description = "Desc", owner = null, users = mutableListOf())

        `when`(projectRepository.findById(1L)).thenReturn(Optional.of(project))
        `when`(userRepository.findById(2L)).thenReturn(Optional.of(user))
        `when`(projectRepository.save(any(Project::class.java))).thenReturn(project)

        val result = projectService.addUserToProject(1L, 2L)
        assertTrue(result)
    }

    @Test
    fun `should throw EntityExistsException when user already in project`() {
        val user = User(id = 2L, username = "testuser2", password = "pass", email = "test2@example.com", firstName = "Test", lastName = "User")
        val project = Project(id = 1L, title = "Project", description = "Desc", owner = null, users = mutableListOf(user))

        `when`(projectRepository.findById(1L)).thenReturn(Optional.of(project))
        `when`(userRepository.findById(2L)).thenReturn(Optional.of(user))

        val exception = assertThrows(EntityExistsException::class.java) {
            projectService.addUserToProject(1L, 2L)
        }
        assertEquals("User already found with id: 2 in project with id: 1", exception.message)
    }

    @Test
    fun `should delete user from project`() {
        val user = User(id = 2L, username = "testuser2", password = "pass", email = "test2@example.com", firstName = "Test", lastName = "User")
        val project = Project(id = 1L, title = "Project", description = "Desc", owner = null, users = mutableListOf(user))

        `when`(projectRepository.findById(1L)).thenReturn(Optional.of(project))
        `when`(userRepository.findById(2L)).thenReturn(Optional.of(user))
        `when`(projectRepository.save(any(Project::class.java))).thenReturn(project)

        val result = projectService.deleteUserFromProject(1L, 2L)
        assertTrue(result)
    }

    @Test
    fun `should throw EntityNotFoundException when user not in project`() {
        val user = User(id = 2L, username = "testuser2", password = "pass", email = "test2@example.com", firstName = "Test", lastName = "User")
        val project = Project(id = 1L, title = "Project", description = "Desc", owner = null, users = mutableListOf())

        `when`(projectRepository.findById(1L)).thenReturn(Optional.of(project))
        `when`(userRepository.findById(2L)).thenReturn(Optional.of(user))

        val exception = assertThrows(EntityNotFoundException::class.java) {
            projectService.deleteUserFromProject(1L, 2L)
        }
        assertEquals("User not found with id: 2 in project with id: 1", exception.message)
    }

    @Test
    fun `should update project`() {
        val project = Project(id = 1L, title = "Old Title", description = "Old Description", owner = null, users = mutableListOf())
        val updateRequest = ProjectUpdateRequestBody(title = "New Title", description = "New Description")

        `when`(projectRepository.findById(1L)).thenReturn(Optional.of(project))
        `when`(projectRepository.save(any(Project::class.java))).thenReturn(project)

        projectService.updateProject(1L, updateRequest)

        assertEquals("New Title", project.title)
        assertEquals("New Description", project.description)
    }
}