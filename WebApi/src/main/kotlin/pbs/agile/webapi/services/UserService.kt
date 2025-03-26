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

    // fun loginUser(usernameOrEmail: String, password: String): UserDto? {
    //     val user = userRepository.findByUsernameOrEmail(usernameOrEmail)
    //     return if (user != null && user.password == password) {
    //         user.toDTO()
    //     } else {
    //         null // Login failed
    //     }
    // }

    // fun saveUser(userDto: UserDto): UserDto {
    //     val user = User(
    //         email = userDto.email,
    //         last_name = userDto.last_name,
    //         username = userDto.username,
    //         first_name = userDto.first_name,
    //         password = userDto.password
    //     )
    //     val savedUser = userRepository.save(user)
    //     return savedUser.toDTO()
    // }
    
//    fun saveUser(userDto: UserDto): UserDto{
//        var user: User = User(
//            email = userDto.email,
//            last_name = userDto.last_name,
//            username = userDto.username,
//            first_name = userDto.first_name,
//            password = userDto.
//        )
//        user = userRepository.save(user).toDTO()
//        return user.TODTO()
//    }

    fun deleteUser(id: Long) = userRepository.deleteById(id)
}



