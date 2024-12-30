package com.example.demo.mapper.row;

import com.example.demo.model.Users;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Users(
                rs.getLong("id"),
                rs.getString("login"),
                rs.getString("passwordHash"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getBoolean("activated"),
                rs.getString("langKey"),
                rs.getString("imageUrl"),
                rs.getString("activationKey"),
                rs.getString("resetKey"),
                rs.getTimestamp("resetDate").toInstant()
        );
    }
}
