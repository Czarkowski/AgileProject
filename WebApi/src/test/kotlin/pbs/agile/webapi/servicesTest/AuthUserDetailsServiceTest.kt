package pbs.agile.webapi.servicesTest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UsernameNotFoundException
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.services.AuthUserDetailsService

@ExtendWith(MockitoExtension::class)
class AuthUserDetailsServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var authUserDetailsService: AuthUserDetailsService

    @Test
    fun `should load user by username or email`() {
        val user = User(
            id = 1L,
            username = "testuser",
            password = "password123",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )
        `when`(userRepository.findByUsernameOrEmail("testuser")).thenReturn(user)

        val userDetails = authUserDetailsService.loadUserByUsername("testuser")

        assertNotNull(userDetails)
        assertEquals("testuser", userDetails.username)
    }

    @Test
    fun `should throw exception when user not found`() {
        `when`(userRepository.findByUsernameOrEmail("unknown")).thenReturn(null)

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            authUserDetailsService.loadUserByUsername("unknown")
        }

        assertEquals("User not found with username or email: unknown", exception.message)
    }
}
