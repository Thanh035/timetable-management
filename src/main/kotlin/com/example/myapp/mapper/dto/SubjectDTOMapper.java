package com.example.myapp.mapper.dto;

import com.example.myapp.domain.dto.SubjectDTO;
import com.example.myapp.domain.model.Subject;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubjectDTOMapper implements Function<Subject, SubjectDTO> {
    @Override
    public SubjectDTO apply(Subject subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getName(),
                subject.getCode(),
                subject.getFormExam()
        );
    }
}