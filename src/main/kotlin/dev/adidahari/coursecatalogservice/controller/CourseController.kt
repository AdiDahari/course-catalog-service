package dev.adidahari.coursecatalogservice.controller

import dev.adidahari.coursecatalogservice.dto.CourseDTO
import dev.adidahari.coursecatalogservice.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDTO: CourseDTO): CourseDTO = courseService.addCourse(courseDTO)


    @GetMapping
    fun retrieveAllCourses(): List<CourseDTO> = courseService.retrieveAllCourses()

    @PutMapping("/{course_id}")
    fun updateCourse(@PathVariable("course_id") courseId: Int, @RequestBody courseDTO: CourseDTO) = courseService.updateCourse(courseId, courseDTO)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId: Int) = courseService.deleteCourse(courseId)
}