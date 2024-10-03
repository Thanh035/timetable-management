package com.example.myapp.api

import com.example.myapp.api.input.SubjectCreateRequest
import com.example.myapp.api.input.SubjectUpdateRequest
import com.example.myapp.domain.dto.SubjectDTO
import com.example.myapp.service.ISubjectService
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/subjects")
@AllArgsConstructor
class SubjectAPI {

    @Autowired
    lateinit var subjectService: ISubjectService

    @GetMapping
    fun getSubjects(): List<SubjectDTO> {
        return subjectService.allSubjects
    }

    @GetMapping("{subjectId}")
    fun getSubject(@PathVariable("subjectId") subjectId: Long): SubjectDTO {
        return subjectService.getSubject(subjectId)
    }

    @PostMapping
    fun addNewSubject(@RequestBody request: SubjectCreateRequest) {
        subjectService.addNewStudent(request)
    }

    @DeleteMapping("{subjectId}")
    fun deleteSubject(@PathVariable("subjectId") subjectId: Long) {
        subjectService.deleteSubjectById(subjectId)
    }

    @PutMapping("{subjectId}")
    fun updateSubject(
            @PathVariable("subjectId") subjectId: Long,
            @RequestBody updateRequest: SubjectUpdateRequest
    ) : SubjectDTO {
        return subjectService.updateSubject(subjectId, updateRequest)
    }

}