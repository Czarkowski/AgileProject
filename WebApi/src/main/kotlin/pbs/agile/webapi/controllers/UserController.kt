package pbs.agile.webapi.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.services.UserService

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<UserDto> = userService.getAllUsers()

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): UserDto? = userService.getUserByUsername(username)

    // @PostMapping("/login")
    // fun login(@RequestBody loginRequest: LoginRequest): UserDto? {
    //     return userService.loginUser(loginRequest.usernameOrEmail, loginRequest.password)
    // }

    // @PostMapping("/register")
    // fun register(@RequestBody userDto: UserDto): UserDto {
    //     return userService.saveUser(userDto)
    // }

//    @PostMapping
//    fun createUser(@RequestBody user: UserDto): UserDto = userService.saveUser(user)
//
//    @DeleteMapping("/{id}")
//    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}
