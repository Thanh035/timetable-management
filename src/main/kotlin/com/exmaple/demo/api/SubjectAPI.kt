package com.exmaple.demo.api

import com.exmaple.demo.api.input.SubjectCreateRequest
import com.exmaple.demo.api.input.SubjectUpdateRequest
import com.exmaple.demo.domain.dto.SubjectDTO
import com.exmaple.demo.service.ISubjectService
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