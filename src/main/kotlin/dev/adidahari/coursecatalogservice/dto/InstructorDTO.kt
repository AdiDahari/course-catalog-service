package dev.adidahari.coursecatalogservice.dto

import javax.validation.constraints.NotBlank

class InstructorDTO (
    val id: Int?,
    @get:NotBlank(message = "InstructorDTO.name must not be blank")
    var name: String
)