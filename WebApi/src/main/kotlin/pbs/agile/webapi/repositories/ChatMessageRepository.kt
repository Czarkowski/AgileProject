package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pbs.agile.webapi.models.entities.ChatMessage

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findByProject_Id(projectId: Long): List<ChatMessage>
}
