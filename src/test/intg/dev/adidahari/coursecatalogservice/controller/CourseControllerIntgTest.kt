package dev.adidahari.coursecatalogservice.controller

import dev.adidahari.coursecatalogservice.dto.CourseDTO
import dev.adidahari.coursecatalogservice.entity.Course
import dev.adidahari.coursecatalogservice.repository.CourseRepository
import dev.adidahari.coursecatalogservice.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {

        courseRepository.deleteAll()

        val courses = courseEntityList()

        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
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
        val courseDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult().responseBody

        println("courseDTOs: $courseDTOs")
        assertEquals(3, courseDTOs!!.size)
    }

    @Test
    fun updateCourse() {
        val course = Course(
            null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development"
        )

        courseRepository.save(course)

        val updatedCourseDTO = CourseDTO(
            null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development"
        )

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}", course.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult().responseBody

        assertEquals("Build RestFul APis using SpringBoot and Kotlin1", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {
        val course = Course(
            null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development"
        )

        courseRepository.save(course)

        val updatedCourseDTO = CourseDTO(
            null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development"
        )

        val updatedCourse = webTestClient.delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent
    }
}