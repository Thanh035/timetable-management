package com.exmaple.demo.service;

import com.exmaple.demo.api.input.SubjectCreateRequest;
import com.exmaple.demo.api.input.SubjectUpdateRequest;
import com.exmaple.demo.domain.dto.SubjectDTO;

import java.util.List;

public interface ISubjectService {
    List<SubjectDTO> getAllSubjects();

    SubjectDTO getSubject(Long id);

    void deleteSubjectById(Long subjectId);

    SubjectDTO updateSubject(Long subjectId, SubjectUpdateRequest updateRequest);

    SubjectDTO addSubject(SubjectCreateRequest request);
}
