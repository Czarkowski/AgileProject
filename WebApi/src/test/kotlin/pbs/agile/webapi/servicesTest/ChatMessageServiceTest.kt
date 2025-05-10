package pbs.agile.webapi.servicesTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import pbs.agile.webapi.dtos.ChatMessageDto
import pbs.agile.webapi.models.entities.ChatMessage
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.ChatMessageRepository
import pbs.agile.webapi.repositories.ProjectRepository
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.services.ChatMessageService
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChatMessageServiceTest {

    private lateinit var chatMessageRepository: ChatMessageRepository
    private lateinit var userRepository: UserRepository
    private lateinit var projectRepository: ProjectRepository
    private lateinit var chatMessageService: ChatMessageService

    @BeforeEach
    fun setup() {
        chatMessageRepository = mock(ChatMessageRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        projectRepository = mock(ProjectRepository::class.java)
        chatMessageService = ChatMessageService(chatMessageRepository, userRepository, projectRepository)
    }

    @Test
    fun `should save and return chat message DTO`() {
        val user = User(
            id = 1,
            username = "john",
            password = "pass",
            email = "john@example.com",
            firstName = "John",
            lastName = "Doe"
        )
        val project = Project(
            id = 1,
            title = "My Project",
            description = "Test desc",
            owner = user
        )
        val dto = ChatMessageDto(
            senderId = 1,
            content = "Hello",
            timestamp = LocalDateTime.parse("2024-01-01T10:00:00")
        )

        `when`(userRepository.findById(1)).thenReturn(Optional.of(user))
        `when`(projectRepository.findById(10)).thenReturn(Optional.of(project))

        val savedMessage = ChatMessage(
            id = 100,
            sender = user,
            content = dto.content,
            timestamp = dto.timestamp,
            project = project
        )

        `when`(chatMessageRepository.save(any(ChatMessage::class.java))).thenReturn(savedMessage)

        val result = chatMessageService.saveChatMessage(dto, 10)

        assertEquals("Hello", result.content)
        assertEquals(LocalDateTime.parse("2024-01-01T10:00:00"), result.timestamp)
        assertEquals(1, result.senderId)
    }

    @Test
    fun `should throw when user not found`() {
        val dto = ChatMessageDto(senderId = 999, content = "Hello", timestamp = LocalDateTime.parse("2024-01-01T10:00:00"))
        `when`(userRepository.findById(999)).thenReturn(Optional.empty())

        val exception = assertThrows<UsernameNotFoundException> {
            chatMessageService.saveChatMessage(dto, 10)
        }

        assertTrue(exception.message!!.contains("User not found"))
    }

    @Test
    fun `should throw when project not found`() {
        val user = User(
            id = 1,
            username = "john",
            password = "pass",
            email = "john@example.com",
            firstName = "John",
            lastName = "Doe"
        )
        val dto = ChatMessageDto(senderId = 1, content = "Hello", timestamp = LocalDateTime.parse("2024-01-01T10:00:00"))

        `when`(userRepository.findById(1)).thenReturn(Optional.of(user))
        `when`(projectRepository.findById(10)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            chatMessageService.saveChatMessage(dto, 10)
        }

        assertTrue(exception.message!!.contains("Project not found"))
    }

    @Test
    fun `should return chat messages for a given project`() {
        val user = User(
            id = 1,
            username = "john",
            password = "pass",
            email = "john@example.com",
            firstName = "John",
            lastName = "Doe"
        )
        val project = Project(
            id = 1,
            title = "My Project",
            description = "Test desc",
            owner = user
        )
        val message = ChatMessage(
            id = 101,
            content = "Test message",
            timestamp = LocalDateTime.parse("2024-01-01T10:00:00"),
            sender = user,
            project = project
        )

        `when`(chatMessageRepository.findByProject_Id(10)).thenReturn(listOf(message))

        val result = chatMessageService.getMessagesForProject(10, LocalDateTime.MIN, LocalDateTime.MAX)

        assertEquals(1, result.size)
        assertEquals("Test message", result[0].content)
        assertEquals(LocalDateTime.parse("2024-01-01T10:00:00"), result[0].timestamp)
        assertEquals(1, result[0].senderId)
    }
}