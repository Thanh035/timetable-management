package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.api.input.SubjectCreateRequest;
import com.mycompany.myapp.api.input.SubjectUpdateRequest;
import com.mycompany.myapp.dao.impl.SubjectDAO;
import com.mycompany.myapp.domain.dto.SubjectDTO;
import com.mycompany.myapp.exception.RequestValidationException;
import com.mycompany.myapp.exception.ResourceNotFoundException;
import com.mycompany.myapp.mapper.dto.SubjectDTOMapper;
import com.mycompany.myapp.domain.model.Subject;
import com.mycompany.myapp.service.ISubjectService;
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

