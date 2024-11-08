package com.mycompany.myapp.dao;

import com.mycompany.myapp.dao.impl.ExcelDataDAO;
import com.mycompany.myapp.mapper.row.ExcelDataRowMapper;
import com.mycompany.myapp.domain.model.Exceldata;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ExcelDataDAOImpl implements ExcelDataDAO {

    private final JdbcTemplate jdbcTemplate;

    private final ExcelDataRowMapper excelDataRowMapper;

    public void insertExcelData(Exceldata exceldata) {
        String sql = "INSERT INTO exceldata (credit_num, course_name, teacher_name, LL,number_of_periods, "
                + "coefficient, number_of_students, grade_coefficient, compliance_standard, note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(
                sql,
                exceldata.getCreditNum(),
                exceldata.getCourseName(),
                exceldata.getTeacherName(),
                exceldata.getLL(),
                exceldata.getNumberOfPeriods(),
                exceldata.getCoefficient(),
                exceldata.getNumberOfStudents(),
                exceldata.getGradeCoefficient(),
                exceldata.getComplianceStandard(),
                exceldata.getNote());
    }

    @Override
    public void updateExcelData(Exceldata update) {
        if (update.getCreditNum() != 0) {
            String sql = "UPDATE exceldata SET credit_num = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getCreditNum(),
                    update.getId()
            );
        }

        if (update.getCourseName() != null) {
            String sql = "UPDATE exceldata SET course_name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getCourseName(),
                    update.getId()
            );
        }

        if (update.getTeacherName() != null) {
            String sql = "UPDATE exceldata SET teacher_name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getTeacherName(),
                    update.getId()
            );
        }

        if (update.getLL() != 0) {
            String sql = "UPDATE exceldata SET ll = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getLL(),
                    update.getId()
            );
        }

        if (update.getNumberOfPeriods() != 0) {
            String sql = "UPDATE exceldata SET number_of_periods = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getNumberOfPeriods(),
                    update.getId()
            );
        }

        if (update.getCoefficient() != null) {
            String sql = "UPDATE exceldata SET coefficient = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getCoefficient(),
                    update.getId()
            );
        }

        if (update.getNumberOfStudents() != 0) {
            String sql = "UPDATE exceldata SET number_of_students = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getNumberOfStudents(),
                    update.getId()
            );
        }

        if (update.getGradeCoefficient() != null) {
            String sql = "UPDATE exceldata SET grade_coefficient = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getGradeCoefficient(),
                    update.getId()
            );
        }

        if (update.getComplianceStandard() != 0) {
            String sql = "UPDATE exceldata SET compliance_standard = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getComplianceStandard(),
                    update.getId()
            );
        }

        if (update.getNote() != null && update.getNote() != "") {
        String sql = "UPDATE exceldata SET note = ? WHERE id = ?";
        int result = jdbcTemplate.update(
                sql,
                update.getNote(),
                update.getId()
        );
        }
    }

    @Override
    public Optional<Exceldata> selectExcelDataById(Long id) {
        var sql = """
                SELECT id, credit_num, course_name, teacher_name, LL, number_of_periods, coefficient, number_of_students, grade_coefficient, compliance_standard, note
                FROM exceldata
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, excelDataRowMapper, id)
                .stream()
                .findFirst();
    }

    public List<Exceldata> findAllBySortId() {
        var sql = """
            SELECT id, credit_num, course_name, teacher_name, LL, number_of_periods, coefficient, number_of_students, grade_coefficient, compliance_standard, note
            FROM exceldata
            ORDER BY id ASC
            LIMIT 1000
            """;
        return jdbcTemplate.query(sql, excelDataRowMapper);
    }
}
