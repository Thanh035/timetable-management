package com.mycompany.myapp.api.input;

public record SubjectUpdateRequest(
        String name,
        String code,
        String formExam
) {
}
