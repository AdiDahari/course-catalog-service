package dev.adidahari.coursecatalogservice.controller

import dev.adidahari.coursecatalogservice.dto.InstructorDTO
import dev.adidahari.coursecatalogservice.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(val instructorService: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody instructorDTO: InstructorDTO) = instructorService.createInstructor(instructorDTO)
}