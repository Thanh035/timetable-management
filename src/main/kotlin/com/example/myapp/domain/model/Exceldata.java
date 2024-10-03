package com.example.myapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exceldata {
    private Long id;

    private int creditNum;

    private String courseName;

    private String teacherName;

    private int LL;

    private int numberOfPeriods;

    private BigDecimal coefficient;

    private int numberOfStudents;

    private BigDecimal gradeCoefficient;

    private int complianceStandard;

    private String note;

}
