package com.mycompany.myapp.mapper.row;

import com.mycompany.myapp.domain.model.Exceldata;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ExcelDataRowMapper implements RowMapper<Exceldata> {
    @Override
    public Exceldata mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Exceldata(
                rs.getLong("id"),
                rs.getInt("credit_num"),
                rs.getString("course_name"),
                rs.getString("teacher_name"),
                rs.getInt("ll"),
                rs.getInt("number_of_periods"),
                rs.getBigDecimal("coefficient"),
                rs.getInt("number_of_students"),
                rs.getBigDecimal("grade_coefficient"),
                rs.getInt("compliance_standard"),
                rs.getString("note")
        );
    }
}
