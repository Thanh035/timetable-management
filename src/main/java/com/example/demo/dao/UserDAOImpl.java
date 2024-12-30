package com.example.demo.dao;

import com.example.demo.dao.impl.UserDAO;
import com.example.demo.mapper.row.UserRowMapper;
import com.example.demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;
    @Override
    public Optional<Users> findOneWithRolesByLogin(String lowercaseLogin) {
        var sql = """
                SELECT *
                FROM users
                WHERE login = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, lowercaseLogin)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Users> findOneByLogin(String login) {
        var sql = """
                SELECT *
                FROM users
                WHERE login = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, login)
                .stream()
                .findFirst();
    }

}
