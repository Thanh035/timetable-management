package com.mycompany.myapp.service;

import com.mycompany.myapp.api.input.SubjectCreateRequest;
import com.mycompany.myapp.api.input.SubjectUpdateRequest;
import com.mycompany.myapp.domain.dto.SubjectDTO;

import java.util.List;

public interface ISubjectService {
    List<SubjectDTO> getAllSubjects();

    SubjectDTO getSubject(Long id);

    void deleteSubjectById(Long subjectId);

    SubjectDTO updateSubject(Long subjectId, SubjectUpdateRequest updateRequest);

    SubjectDTO addSubject(SubjectCreateRequest request);
}
