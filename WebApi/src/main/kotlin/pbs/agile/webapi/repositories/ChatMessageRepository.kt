package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pbs.agile.webapi.models.entities.ChatMessage
import java.time.LocalDateTime

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findByProject_Id(projectId: Long): List<ChatMessage>

    @Query("""
    SELECT m FROM ChatMessage m 
    WHERE m.project.id = :projectId
    AND (m.timestamp >= :dateFrom)
    AND (m.timestamp <= :dateTo)
""")
    fun findByProjectIdAndDateRange(
        @Param("projectId") projectId: Long,
        @Param("dateFrom") dateFrom: LocalDateTime,
        @Param("dateTo") dateTo: LocalDateTime
    ): List<ChatMessage>

//    fun findByProjectIdAndOptionalDates(
//        projectId: Long,
//        dateFrom: LocalDateTime?,
//        dateTo: LocalDateTime?
//    ): List<ChatMessage> {
//        val messages = findByProject_Id(projectId)
//        return messages.filter {
//            (dateFrom == null || !it.timestamp.isBefore(dateFrom)) &&
//                    (dateTo == null || !it.timestamp.isAfter(dateTo))
//        }
//    }
}
