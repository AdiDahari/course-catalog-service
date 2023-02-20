package dev.adidahari.coursecatalogservice.controller

import com.ninjasquad.springmockk.MockkBean
import dev.adidahari.coursecatalogservice.dto.CourseDTO
import dev.adidahari.coursecatalogservice.entity.Course
import dev.adidahari.coursecatalogservice.service.CourseService
import dev.adidahari.coursecatalogservice.util.courseDTO
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val courseDTO = CourseDTO(
            null,
            "Build Restful APIs using Springboot and Kotlin",
            "Development"
        )

        val savedCourseDTO = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult().responseBody

        assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {

        every { courseServiceMock.retrieveAllCourses() }.returnsMany(
            listOf(
                courseDTO(id = 1),
                courseDTO(id = 2, name = "Build Restful APIs using Springboot and Kotlin")
            )
        )

        val courseDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult().responseBody

        println("courseDTOs: $courseDTOs")
        assertEquals(2, courseDTOs!!.size)
    }

    @Test
    fun updateCourse() {

        every { courseServiceMock.updateCourse(any(), any()) } returns courseDTO(100, name = "Build RestFul APis using SpringBoot and Kotlin1")

        val updatedCourseDTO = CourseDTO(
            null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development"
        )

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult().responseBody

        assertEquals("Build RestFul APis using SpringBoot and Kotlin1", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {

        every { courseServiceMock.deleteCourse(any()) } just runs

        val updatedCourse = webTestClient.delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    fun addCourse_validation() {

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val courseDTO = CourseDTO(
            null,
            "",
            ""
        )

        val response = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("CourseDTO.category must not be blank, CourseDTO.name must not be blank", response)

    }

    @Test
    fun addCourse_runtimeException() {
        val courseDTO = CourseDTO(
            null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development"
        )

        val errorMessage = "Unexpected Error occured"
        every { courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)


        val response = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)

    }
}