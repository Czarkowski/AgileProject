package pbs.agile.webapi.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import pbs.agile.webapi.auth.JwtUtil
import pbs.agile.webapi.mappers.toLoggedUserDTO
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.requests.AuthTokensResponseBody
import pbs.agile.webapi.requests.LoginRequestBody
import pbs.agile.webapi.requests.RefreshTokenRequestBody
import pbs.agile.webapi.requests.RegisterRequestBody
import pbs.agile.webapi.services.AuthUserDetailsService

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
    fun register(@RequestBody request: RegisterRequestBody): ResponseEntity<String> {
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
    fun login(@RequestBody request: LoginRequestBody): ResponseEntity<AuthTokensResponseBody> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.identifier, request.password)
            )
            val authorities = authentication.authorities
            val roles = authorities.stream().map { it.authority }.toList()

            val token = jwtUtil.generateToken(authentication.name, roles)
            val refreshToken = jwtUtil.generateRefreshToken(request.identifier)

            val userDetails = userRepository.findByUsername(authentication.name)!!
            val loggedUser = userDetails.toLoggedUserDTO()

            var res: AuthTokensResponseBody = AuthTokensResponseBody(
                token = token,
                refreshToken = refreshToken,
                loggedUser = loggedUser,
            )

            ResponseEntity.status(HttpStatus.OK).body(res)

        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequestBody): ResponseEntity<AuthTokensResponseBody> {
        return try {
            if (!jwtUtil.isTokenValid(request.refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            }
            val username: String = jwtUtil.extractUsername(request.refreshToken)!!
            val userDetails = userDetailsService.loadUserByUsername(username)

            val newAccessToken = jwtUtil.generateToken(username, userDetails.authorities.stream().map { it.authority }.toList())

            var res: AuthTokensResponseBody = AuthTokensResponseBody(
                token = newAccessToken,
            )
            ResponseEntity.ok(res)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}

