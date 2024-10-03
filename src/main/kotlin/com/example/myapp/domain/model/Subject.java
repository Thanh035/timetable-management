package com.example.myapp.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    private Long id;

    private String name;

    private String code;

    private String formExam;

    private java.sql.Timestamp createdAt;

    private java.sql.Timestamp updatedAt;

    private java.sql.Timestamp deletedAt;

}