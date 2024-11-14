package com.exmaple.demo.service.impl;

import com.exmaple.demo.api.input.SubjectCreateRequest;
import com.exmaple.demo.api.input.SubjectUpdateRequest;
import com.exmaple.demo.domain.dto.SubjectDTO;
import com.exmaple.demo.domain.model.Subject;
import com.exmaple.demo.dao.impl.SubjectDAO;
import com.exmaple.demo.exception.RequestValidationException;
import com.exmaple.demo.exception.ResourceNotFoundException;
import com.exmaple.demo.mapper.dto.SubjectDTOMapper;
import com.exmaple.demo.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements ISubjectService {

    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private SubjectDTOMapper subjectDTOMapper;

    public List<SubjectDTO> getAllSubjects() {
        return subjectDAO.selectAllSubjects()
                .stream()
                .map(subjectDTOMapper)
                .collect(Collectors.toList());
    }

    public SubjectDTO getSubject(Long id) {
        return subjectDAO.selectSubjectById(id)
                .map(subjectDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "subject with id [%s] not found".formatted(id)
                ));
    }

    public void deleteSubjectById(Long subjectId) {
        subjectDAO.deleteSubjectById(subjectId);
    }

    public SubjectDTO updateSubject(Long subjectId, SubjectUpdateRequest updateRequest) {
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

    public SubjectDTO addSubject(SubjectCreateRequest request) {
//        // check if email exists
//        String email = customerRegistrationRequest.email();
//        if (customerDao.existsCustomerWithEmail(email)) {
//            throw new DuplicateResourceException(
//                    "email already taken"
//            );
//        }

        //add
        Subject subject = new Subject();
        subject.setCode(request.code());
        subject.setName(request.name());
        return subjectDTOMapper.apply(subjectDAO.insertSubject(subject));
    }
}

