package pbs.agile.webapi.models.entities

import kotlin.collections.*
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
@Table(name = "ChatMessages")
data class ChatMessage(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(nullable = false)
        val content: String,
        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        val timestamp: LocalDateTime,
        @ManyToOne
        @JoinColumn(name = "sender_id", nullable = false)
        val sender: User,
        @ManyToOne
        @JoinColumn(name = "project_id", nullable = false)
        val project: Project
)