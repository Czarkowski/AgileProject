package pbs.agile.webapi.servicesTest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.services.UserService


@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = User(
            id = 1L,
            username = "testuser",
            password = "password123",
            email = "test@example.com",
            firstName = "Test",
            lastName = "User"
        )
    }

    @Test
    fun `should return all users as UserDto`() {
        `when`(userRepository.findAll()).thenReturn(listOf(user))

        val users = userService.getAllUsers()

        assertEquals(1, users.size)
        assertEquals(user.username, users[0].username)
    }

    @Test
    fun `should return user by username`() {
        `when`(userRepository.findByUsername("testuser")).thenReturn(user)

        val result = userService.getUserByUsername("testuser")

        assertNotNull(result)
        assertEquals(user.username, result?.username)
    }

    @Test
    fun `should delete user by id`() {
        doNothing().`when`(userRepository).deleteById(1L)

        assertDoesNotThrow { userService.deleteUser(1L) }
        verify(userRepository, times(1)).deleteById(1L)
    }
}
