package pbs.agile.webapi.services

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pbs.agile.webapi.repositories.UserRepository

@Service
class AuthUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        val user = userRepository.findByUsernameOrEmail(usernameOrEmail)
            ?: throw UsernameNotFoundException("User not found with username or email: $usernameOrEmail")

        return User.withUsername(user.username)
            .password(user.password)
            .authorities("Admin") // Jeśli masz role w bazie
            .build()
    }
}
