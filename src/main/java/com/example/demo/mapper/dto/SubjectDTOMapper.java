package com.example.demo.mapper.dto;


import com.example.demo.dto.AdminSubjectDTO;
import com.example.demo.model.Subject;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class SubjectDTOMapper implements Function<Subject, AdminSubjectDTO> {
    @Override
    public AdminSubjectDTO apply(Subject subject) {
        return new AdminSubjectDTO(
                subject.getId(),
                subject.getName(),
                subject.getCode(),
                subject.getFormExam()
        );
    }
}
