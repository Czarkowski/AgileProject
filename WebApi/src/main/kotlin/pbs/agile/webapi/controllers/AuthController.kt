package pbs.agile.webapi.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.auth.JwtUtil
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        if (userRepository.findByUsername(request.username) != null) {
            return ResponseEntity.badRequest().body("User already exists")
        }
//        User(
//            Username = "dsfingu"
//        )
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            email = request.email
        )
        userRepository.save(user)
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password)
            )
            val token = jwtUtil.generateToken(request.username)
            ResponseEntity.ok(token)
        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
        }
    }
}

data class RegisterRequest(val username: String, val password: String, val email: String)
data class LoginRequest(val username: String, val password: String)
