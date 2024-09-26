package com.example.myapp.api.input;

public record SubjectUpdateRequest(
        String name,
        String code,
        String formExam
) {
}
