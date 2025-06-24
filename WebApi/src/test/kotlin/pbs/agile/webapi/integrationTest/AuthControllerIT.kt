package pbs.agile.webapi.integrationTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.transaction.annotation.Transactional
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.repositories.UserRepository

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setup() {
        val user = User(
            username = "testuser",
            password = passwordEncoder.encode("password123"),
            email = "test@example.com",
            first_name = "Test",
            last_name = "User"
        )
        userRepository.save(user)
    }

    @Test
    fun `should register new user successfully`() {
        val json = """
            {
                "username": "newuser",
                "password": "newpass123",
                "email": "new@example.com",
                "first_name": "New",
                "last_name": "User"
            }
        """.trimIndent()

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isOk() }
            content { string("User registered successfully") }
        }
    }

    @Test
    fun `should not register user that already exists`() {
        val json = """
            {
                "username": "testuser",
                "password": "whatever",
                "email": "dup@example.com",
                "first_name": "Dup",
                "last_name": "User"
            }
        """.trimIndent()

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isBadRequest() }
            content { string("User already exists") }
        }
    }

    @Test
    fun `should login successfully with valid credentials`() {
        val json = """
            {
                "identifier": "testuser",
                "password": "password123"
            }
        """.trimIndent()

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isOk() }
            jsonPath("$.token").exists()
            jsonPath("$.refreshToken").exists()
            jsonPath("$.loggedUser.username").value("testuser")
        }
    }

    @Test
    fun `should fail login with invalid credentials`() {
        val json = """
            {
                "identifier": "testuser",
                "password": "wrongpassword"
            }
        """.trimIndent()

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isUnauthorized() }
        }
    }
}