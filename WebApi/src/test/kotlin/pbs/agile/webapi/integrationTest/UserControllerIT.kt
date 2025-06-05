package pbs.agile.webapi.integrationTest

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pbs.agile.webapi.repositories.UserRepository
import pbs.agile.webapi.models.entities.User
import pbs.agile.webapi.requests.LoginRequestBody
import pbs.agile.webapi.requests.UserUpdateRequestBody

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIT @Autowired constructor(
    val context: WebApplicationContext,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
) {

    lateinit var mockMvc: MockMvc
    lateinit var objectMapper: ObjectMapper
    lateinit var jwtToken: String

    @BeforeAll
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
        objectMapper = ObjectMapper()

        val user = User(
            username = "testuser",
            password = passwordEncoder.encode("password123"),
            email = "test@example.com",
            first_name = "Test",
            last_name = "User"
        )
        userRepository.save(user)

        // Login and extract JWT
        val loginPayload = objectMapper.writeValueAsString(
            LoginRequestBody(identifier = "testuser", password = "password123")
        )

        val loginResult = mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginPayload)
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseJson = loginResult.response.contentAsString
        val tokenNode = objectMapper.readTree(responseJson).get("token")
        jwtToken = tokenNode.asText()
    }

    @AfterAll
    fun cleanup() {
        userRepository.deleteAll()
    }

    @Test
    fun `should return list of users`() {
        mockMvc.perform(
            get("/api/users")
                .header("Authorization", "Bearer $jwtToken")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$[0].username").value("testuser"))
    }

    @Test
    fun `should return user by username`() {
        mockMvc.perform(
            get("/api/users/testuser")
                .header("Authorization", "Bearer $jwtToken")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.email").value("test@example.com"))
    }

//    @Test
//    fun `should update user data`() {
//        val updateRequest = UserUpdateRequestBody(
//            firstName = "Updated",
//            lastName = "Name"
//        )
//
//        val payload = objectMapper.writeValueAsString(updateRequest)
//
//        mockMvc.perform(
//            put("/api/users/edit-user")
//                .header("Authorization", "Bearer $jwtToken")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(payload)
//        ).andExpect(status().isOk)
//            .andExpect(jsonPath("$.email").value("new@example.com"))
//            .andExpect(jsonPath("$.firstName").value("Updated"))
//    }
}
