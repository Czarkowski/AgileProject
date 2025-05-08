package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pbs.agile.webapi.models.entities.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun getBy(): List<User>
    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    fun findByUsernameOrEmail(@Param("identifier") identifier: String): User?
    fun findByEmailContainingIgnoreCase(email: String): MutableList<User>
    fun findByFirstNameIsContainingIgnoreCaseAndLastNameIsContainingIgnoreCase(firstName: String, lastName: String): MutableList<User>
    fun findByUsernameContainingIgnoreCaseOrEmailContainingOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(username: String, email: String, firstName: String, lastName: String): MutableList<User>
}
