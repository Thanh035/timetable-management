package com.example.myapp.dao.impl;

import com.example.myapp.model.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SubjectDAO {
    List<Subject> selectAllSubjects();

    Optional<Subject> selectSubjectById(Long subjectId);

    void insertSubject(Subject subject);

    void updateSubject(Subject update);

    void deleteSubjectById(Long subjectId);
}
