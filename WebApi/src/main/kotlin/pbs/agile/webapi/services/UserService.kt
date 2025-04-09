package pbs.agile.webapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.mappers.toDTO
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository

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
}



