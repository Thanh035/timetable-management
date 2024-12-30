package com.example.demo.dao.impl;

import com.example.demo.model.PersistentTokens;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PersistentTokensDAO {
    void saveAndFlush(PersistentTokens token);

    Optional<PersistentTokens> findById(String presentedSeries);
}
