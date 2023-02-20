package dev.adidahari.coursecatalogservice.repository

import dev.adidahari.coursecatalogservice.entity.Course
import org.springframework.data.repository.CrudRepository


interface CourseRepository: CrudRepository<Course, Int> {
}