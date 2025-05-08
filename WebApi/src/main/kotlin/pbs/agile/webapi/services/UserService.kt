package pbs.agile.webapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.mappers.toDTO
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.requests.UserUpdateRequestBody

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun getAllUsers(): List<UserDto> {
        var users: List<User> = userRepository.findAll();
        var res = users.stream().map { it.toDTO() } .toList();
        return res;
    };
    fun getUserByUsername(username: String): UserDto? = userRepository.findByUsername(username)?.toDTO()
    fun getUserByIdentifier(identifier: String): UserDto? = userRepository.findByUsernameOrEmail(identifier)?.toDTO()
    fun deleteUser(id: Long) = userRepository.deleteById(id)

    fun getUsersForSearchString(searchString: String): List<UserDto> {
        val (firstName: String?, lastName: String?, email: String?) = parseSearchString(searchString)

        val users = when {
            email != null -> userRepository.findByEmailContainingIgnoreCase(email)
            firstName != null && lastName != null -> userRepository.findByFirstNameIsContainingIgnoreCaseAndLastNameIsContainingIgnoreCase(firstName, lastName)
            else -> userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchString, searchString, searchString, searchString)
        }
        val res = users.map { it.toDTO() }
        return res
    }

    fun parseSearchString(searchString: String): Triple<String?, String?, String?> {
        // Jeśli searchString zawiera '@', traktujemy to jako email
        if (searchString.contains("@")) {
            return Triple(null, null, searchString)
        }

        // Jeśli searchString zawiera spację, traktujemy to jako imię i nazwisko
        val parts = searchString.split(" ")
        if (parts.size == 2) {
            return Triple(parts[0], parts[1], null)
        }

        // W przeciwnym razie traktujemy to jako nazwę użytkownika
        return Triple(null, null, null)
    }

    fun updateUser(username: String, request: UserUpdateRequestBody): UserDto {
        if (username.isBlank()) {
            throw IllegalArgumentException("Nazwa użytkownika nie może być pusta")
        }

        val user = userRepository.findByUsername(username)
            ?: throw NoSuchElementException("Użytkownik o nazwie '$username' nie istnieje")

        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }

        userRepository.save(user)

        return user.toDTO()
    }
}



