package dev.adidahari.coursecatalogservice.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "CourseDTO.name must not be blank")
    val name: String,
    @get:NotBlank(message = "CourseDTO.category must not be blank")
    val category: String,
    @get:NotNull(message = "CourseDTO.instructorId must not be blank")
    val instructorId: Int? = null
)
