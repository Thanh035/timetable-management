package com.example.myapp.dao;

import com.example.myapp.dao.impl.SubjectDAO;
import com.example.myapp.mapper.row.SubjectRowMapper;
import com.example.myapp.domain.model.Subject;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class SubjectDAOImpl implements SubjectDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SubjectRowMapper subjectRowMapper;

    public List<Subject> selectAllSubjects() {
        var sql = """
                SELECT id, name, code, form_exam, createdat, updatedat, deletedat
                FROM subjects
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, subjectRowMapper);
    }

    public Optional<Subject> selectSubjectById(Long id) {
        var sql = """
                SELECT id, name, code, form_exam, createdAt, updatedAt, deletedAt
                FROM subjects
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, subjectRowMapper, id)
                .stream()
                .findFirst();
    }

    public void insertSubject(Subject subject) {
        var sql = """
                INSERT INTO subjects(name, code)
                VALUES (?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                subject.getName(),
                subject.getCode()
        );
    }

    public Subject updateSubject(Subject update) {
        if (update.getName() != null) {
            String sql = "UPDATE subjects SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getId()
            );
        }

        if (update.getCode() != null) {
            String sql = "UPDATE subjects SET code = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getCode(),
                    update.getId()
            );
        }

        if (update.getFormExam() != null) {
            String sql = "UPDATE subjects SET form_exam = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getFormExam(),
                    update.getId());
        }
        return update;
    }

    public void deleteSubjectById(Long subjectId) {
        var sql = """
                DELETE
                FROM subjects
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, subjectId);
    }
}
