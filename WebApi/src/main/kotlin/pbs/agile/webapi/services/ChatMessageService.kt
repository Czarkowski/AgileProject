package pbs.agile.webapi.services

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pbs.agile.webapi.dtos.ChatMessageDto
import pbs.agile.webapi.mappers.toDTO
import pbs.agile.webapi.models.entities.ChatMessage
import pbs.agile.webapi.repositories.ChatMessageRepository
import pbs.agile.webapi.repositories.ProjectRepository
import pbs.agile.webapi.repositories.UserRepository

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository
) {

    fun saveChatMessage(messageDto: ChatMessageDto, projectId: Long): ChatMessageDto
    {
        val user = userRepository.findById(messageDto.senderId)
            .orElseThrow { UsernameNotFoundException("User not found with ID: ${messageDto.senderId}") }
        val project = projectRepository.findById(projectId)
            .orElseThrow { RuntimeException("Project not found with ID: $projectId") }

        val chatMessage = ChatMessage(
            sender = user,
            content = messageDto.content,
            timestamp = messageDto.timestamp,
            project = project
        )

        // Zapisanie do bazy
        val savedMessage = chatMessageRepository.save(chatMessage)
        return savedMessage.toDTO()
    }

    fun getMessagesForProject(projectId: Long): List<ChatMessageDto> {
        val messages = chatMessageRepository.findByProject_Id(projectId)
        return messages.map { it.toDTO() }
    }
}