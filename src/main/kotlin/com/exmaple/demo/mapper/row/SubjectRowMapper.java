package com.exmaple.demo.mapper.row;

import com.exmaple.demo.domain.model.Subject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubjectRowMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Subject(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("code"),
                rs.getString("form_exam"),
                rs.getTimestamp("createdat"),
                rs.getTimestamp("updatedat"),
                rs.getTimestamp("deletedat")
        );
    }

}
