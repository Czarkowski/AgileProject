package pbs.agile.webapi.models.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

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
    @Column(nullable = false)
    val email: String
) {
    constructor(username: String, password: String, email: String) : this(null, username, password, email){

    }
    constructor() : this(null, "user_name", "password", "") {

    }
}