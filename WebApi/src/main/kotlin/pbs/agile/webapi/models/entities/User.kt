package pbs.agile.webapi.models.entities

import kotlin.collections.*
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.internal.util.collections.CollectionHelper.listOf

@Entity
@Table(name = "Users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotNull
    @field:Size(min = 2, max = 50)
    @Column(nullable = false, unique = true)
    val username: String,

    @field:NotNull
    @field:Size(min = 6)
    @Column(nullable = false)
    val password: String,

    @field:NotNull
    @field:Email
    @Column(nullable = false, unique = true)
    val email: String,

    @field:NotNull
    @field:Size(min = 2, max = 50)
    @Column(nullable = false, unique = false)
    val first_name: String,

    @field:NotNull
    @field:Size(min = 2, max = 50)
    @Column(nullable = false, unique = false)
    val last_name: String,

    @ManyToMany(mappedBy = "users")
    val projects: MutableList<Project> = listOf<Project>(),

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val ownedProjects: MutableList<Project> = listOf()

) {
    constructor(username: String, password: String, email: String, first_name: String, last_name: String) : this(null, username, password, email, first_name, last_name){

    }
    constructor() : this(null, "user_name", "password", "", "", "")
}