package com.example.demo.dao.impl;

import com.example.demo.model.Users;

import java.util.Optional;

public interface UserDAO {

    Optional<Users> findOneWithRolesByLogin(String lowercaseLogin);

    Optional<Users> findOneByLogin(String login);
}
