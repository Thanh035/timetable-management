package com.exmaple.demo.api.input;

public record SubjectUpdateRequest(
        String name,
        String code,
        String formExam
) {
}
