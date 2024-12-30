package com.example.demo.dao;

import com.example.demo.dao.impl.PersistentTokensDAO;
import com.example.demo.model.PersistentTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersistentTokensDAOImpl implements PersistentTokensDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveAndFlush(PersistentTokens token) {
        var sql = """
                INSERT INTO persistent_tokens (series, user_id, token_value, token_date, ip_address, user_agent)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                sql,
                token.getSeries(),
                token.getUserId(),
                token.getTokenValue(),
                token.getTokenDate(),
                token.getIpAddress(),
                token.getUserAgent()
        );
    }

    @Override
    public Optional<PersistentTokens> findById(String presentedSeries) {
        return Optional.empty();
    }
}
