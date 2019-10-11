package dk.cngroup.lentils.api

import dk.cngroup.lentils.dto.ContactFormDTO
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactRestControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun contactUrl() {
        val result = testRestTemplate.getForEntity("/api/contact", ContactFormDTO::class.java)
        assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    fun nonsenseUrl() {
        val result = testRestTemplate.getForEntity("/nonsense", ContactFormDTO::class.java)
        assertEquals(result.statusCode, HttpStatus.NOT_FOUND)
    }
}
