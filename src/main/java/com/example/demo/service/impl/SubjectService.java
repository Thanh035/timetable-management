package com.example.demo.service.impl;


import com.example.demo.api.input.SubjectCreateRequest;
import com.example.demo.api.input.SubjectUpdateRequest;
import com.example.demo.dao.impl.SubjectDAO;
import com.example.demo.dto.AdminSubjectDTO;
import com.example.demo.exception.RequestValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.dto.SubjectDTOMapper;
import com.example.demo.model.Subject;
import com.example.demo.service.ISubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements ISubjectService {

    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private SubjectDTOMapper subjectDTOMapper;

    @Override
    public List<AdminSubjectDTO> getAllSubjects() {
        return subjectDAO.selectAllSubjects()
                .stream()
                .map(subjectDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public AdminSubjectDTO getSubject(Long id) {
        return subjectDAO.selectSubjectById(id)
                .map(subjectDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "subject with id [%s] not found".formatted(id)
                ));
    }

    @Override
    public void deleteSubjectById(Long subjectId) {
        subjectDAO.deleteSubjectById(subjectId);
    }

    @Override
    public void deleteSubjects(Long[] ids) {
        Arrays.stream(ids).parallel().forEach(id -> {
            deleteSubjectById(id);
        });
    }

    @Override
    public AdminSubjectDTO updateSubject(Long subjectId, SubjectUpdateRequest updateRequest) {
        Subject subject = subjectDAO.selectSubjectById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "subject with id [%s] not found".formatted(subjectId)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(subject.getName())) {
            subject.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.code() != null && !updateRequest.code().equals(subject.getCode())) {
            subject.setCode(updateRequest.code());
            changes = true;
        }

        if (updateRequest.formExam() != null && !updateRequest.formExam().equals(subject.getFormExam())) {
            subject.setFormExam(updateRequest.formExam());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        return subjectDTOMapper.apply(subjectDAO.updateSubject(subject));
    }

    @Override
    public AdminSubjectDTO addNewStudent(SubjectCreateRequest request) {
//        // check if email exists
//        String email = customerRegistrationRequest.email();
//        if (customerDao.existsCustomerWithEmail(email)) {
//            throw new DuplicateResourceException(
//                    "email already taken"
//            );
//        }

        //add
        Subject subject = new Subject();
        BeanUtils.copyProperties(request, subject);
        return subjectDTOMapper.apply(subjectDAO.insertSubject(subject));
    }
}

