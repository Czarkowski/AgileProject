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
import pbs.agile.webapi.services.AuthUserDetailsService
import java.util.*

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = ["http://localhost:5173"])
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: AuthUserDetailsService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        if (userRepository.findByUsername(request.username) != null) {
            return ResponseEntity.badRequest().body("User already exists")
        }
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            first_name = request.first_name,
            last_name = request.last_name
        )
        return try {
            userRepository.save(user)
            ResponseEntity.ok("User registered successfully")
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: ${e.message}")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.identifier, request.password)
            )
            val authorities = authentication.authorities
            val roles = authorities.stream().map { it.authority }.toList()

            val token = jwtUtil.generateToken(request.identifier, roles)
            val refreshToken = jwtUtil.generateRefreshToken(request.identifier)

            var res: AuthTokensResponse = AuthTokensResponse(
                token = token,
                refreshToken = refreshToken
            )

            ResponseEntity.status(HttpStatus.OK).body(res)

        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
        }
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<Any> {
        return try {
            if (!jwtUtil.isTokenValid(request.refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")
            }
            val username: String = jwtUtil.extractUsername(request.refreshToken)!!
            val userDetails = userDetailsService.loadUserByUsername(username)

            val newAccessToken = jwtUtil.generateToken(username, userDetails.authorities.stream().map { it.authority }.toList())

            var res: AuthTokensResponse = AuthTokensResponse(
                token = newAccessToken,
                refreshToken = request.refreshToken
            )
            ResponseEntity.ok(res)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token refresh failed")
        }
    }
}

data class RegisterRequest(val username: String, val password: String, val email: String, val first_name: String, val last_name: String)
data class LoginRequest(val identifier: String, val password: String)
data class RefreshTokenRequest(val refreshToken: String)
data class AuthTokensResponse(val token: String, val refreshToken: String)