package pbs.agile.webapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pbs.agile.webapi.dtos.UserDto
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

    fun saveUser(userDto: UserDto): UserDto{
//        userRepository.save(user).toDTO()
        return userDto
    }

    fun deleteUser(id: Long) = userRepository.deleteById(id)
}



fun User.toDTO(): UserDto {
    return UserDto(
        id = this.id ?: 0L,
        username = this.username,
        email = this.email
    )
}