package com.example.demo.dao.impl;


import com.example.demo.model.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SubjectDAO {
    List<Subject> selectAllSubjects();

    Optional<Subject> selectSubjectById(Long subjectId);

    Subject insertSubject(Subject subject);

    Subject updateSubject(Subject update);

    void deleteSubjectById(Long subjectId);
}
