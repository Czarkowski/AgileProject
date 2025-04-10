package pbs.agile.webapi.models.entities

import kotlin.collections.*
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.internal.util.collections.CollectionHelper.listOf

@Entity
@Table(name = "ChatMessages")
data class ChatMessage(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(nullable = false)
        val content: String,
        val timestamp: String,
        @ManyToOne
        @JoinColumn(name = "sender_id", nullable = false)
        val sender: User,
        @ManyToOne
        @JoinColumn(name = "project_id", nullable = false)
        val project: Project
)