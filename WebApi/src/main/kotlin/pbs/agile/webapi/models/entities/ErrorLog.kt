package pbs.agile.webapi.models.entities

import jakarta.persistence.*;
import java.time.LocalDateTime

@Entity
@Table(name = "ErrorLogs")
data class ErrorLog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(name = "error_type", nullable = false)
    val errorType: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val message: String,

    @Column(name = "stack_trace", columnDefinition = "TEXT", nullable = false)
    val stackTrace: String,

    @Column(columnDefinition = "TEXT")
    val cause: String? = null
)
