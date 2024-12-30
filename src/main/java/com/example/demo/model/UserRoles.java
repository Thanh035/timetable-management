package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_roles")
@Getter
@Setter
public class UserRoles {
    private Long userId;
    private String roleName;

}
