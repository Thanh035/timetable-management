package com.example.demo.service;


import com.example.demo.api.input.SubjectCreateRequest;
import com.example.demo.api.input.SubjectUpdateRequest;
import com.example.demo.dto.AdminSubjectDTO;

import java.util.List;

public interface ISubjectService {
    List<AdminSubjectDTO> getAllSubjects();

    AdminSubjectDTO getSubject(Long id);

    void deleteSubjectById(Long subjectId);

    void deleteSubjects(Long[] ids);

    AdminSubjectDTO updateSubject(Long subjectId, SubjectUpdateRequest updateRequest);

    AdminSubjectDTO addNewStudent(SubjectCreateRequest request);
}
