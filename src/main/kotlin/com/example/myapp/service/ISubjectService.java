package com.example.myapp.service;

import com.example.myapp.api.input.SubjectCreateRequest;
import com.example.myapp.api.input.SubjectUpdateRequest;
import com.example.myapp.dto.SubjectDTO;

import java.util.List;

public interface ISubjectService {
    List<SubjectDTO> getAllSubjects();

    SubjectDTO getSubject(Long id);

    void deleteSubjectById(Long subjectId);

    void updateSubject(Long subjectId, SubjectUpdateRequest updateRequest);

    void addNewStudent(SubjectCreateRequest request);
}
