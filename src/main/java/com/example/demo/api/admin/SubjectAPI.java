package com.example.demo.api.admin;

import com.example.demo.api.input.SubjectCreateRequest;
import com.example.demo.api.input.SubjectUpdateRequest;
import com.example.demo.dto.AdminSubjectDTO;
import com.example.demo.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subjects")
public class SubjectAPI {
    @Autowired
    private ISubjectService subjectService;

    @GetMapping
    public List<AdminSubjectDTO> getSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("{subjectId}")
    public AdminSubjectDTO getSubject(@PathVariable("subjectId") Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @PostMapping
    public AdminSubjectDTO addNewSubject(@RequestBody SubjectCreateRequest request) {
        return subjectService.addNewStudent(request);
    }

    @DeleteMapping
    public void deleteSubjects(@RequestBody Long[] ids) {
        subjectService.deleteSubjects(ids);
    }

    @DeleteMapping("{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") Long subjectId) {
        subjectService.deleteSubjectById(subjectId);
    }

    @PutMapping("{subjectId}")
    public AdminSubjectDTO updateSubject(@PathVariable("subjectId") Long subjectId, @RequestBody SubjectUpdateRequest updateRequest) {
        return subjectService.updateSubject(subjectId, updateRequest);
    }
}
