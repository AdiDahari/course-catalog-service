package dev.adidahari.coursecatalogservice.service

import dev.adidahari.coursecatalogservice.dto.InstructorDTO
import dev.adidahari.coursecatalogservice.entity.Instructor
import dev.adidahari.coursecatalogservice.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {

        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }

        instructorRepository.save(instructorEntity)

        return instructorEntity.let {
            InstructorDTO(it.id, it.name)
        }
    }

    fun finfByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)

    }
}