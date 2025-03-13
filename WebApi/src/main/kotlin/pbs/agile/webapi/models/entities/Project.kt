package pbs.agile.webapi.models.entities

import kotlin.collections.*
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.internal.util.collections.CollectionHelper.listOf

@Entity
@Table(name = "Projects")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotNull
    @field:Size(min = 2, max = 50)
    @Column(nullable = false, unique = true)
    var title: String,

    @field:NotNull
    @field:Size(min = 0, max = 50)
    @Column(nullable = false, unique = false)
    var description: String,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "project_users",
        joinColumns = [JoinColumn(name = "project_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var users: MutableList<User> = listOf<User>(),
)
{
    constructor(): this(null, "", "")
}
