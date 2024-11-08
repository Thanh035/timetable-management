package com.mycompany.myapp.api

import com.mycompany.myapp.api.input.SubjectCreateRequest
import com.mycompany.myapp.api.input.SubjectUpdateRequest
import com.mycompany.myapp.domain.dto.SubjectDTO
import com.mycompany.myapp.service.ISubjectService
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/subjects")
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
    fun addSubject(@RequestBody request: SubjectCreateRequest) : SubjectDTO {
        return subjectService.addSubject(request)
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