package pbs.agile.webapi.controllers

import org.intellij.lang.annotations.Identifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.dtos.UserDto
import pbs.agile.webapi.services.UserService

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["http://localhost:5173"])
class UserController(@Autowired private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<UserDto> = userService.getAllUsers()

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): UserDto? = userService.getUserByUsername(username)
    @GetMapping("/user")
    fun getUserByIdentifier(@RequestParam  identifier: String): UserDto? = userService.getUserByIdentifier(identifier)
}
