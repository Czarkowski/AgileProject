package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pbs.agile.webapi.models.entities.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(Username: String): User?
    fun getBy(): List<User>
}
