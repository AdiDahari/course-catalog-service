package dev.adidahari.coursecatalogservice.repository

import dev.adidahari.coursecatalogservice.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository: CrudRepository<Instructor, Int> {
}