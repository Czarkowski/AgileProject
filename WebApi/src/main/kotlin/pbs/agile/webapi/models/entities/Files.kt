package pbs.agile.webapi.models.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "files")
class Files(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    var project: Project,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    var uploader: User,

    @Column(nullable = false)
    var filename: String,

    @Column(nullable = false)
    var filepath: String,

    @Column(nullable = false)
    var fileType: String,

    @Column(nullable = false)
    var fileSize: Long,

    @Column(nullable = false)
    var uploadDate: LocalDateTime = LocalDateTime.now()
)