package com.exmaple.demo.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Articles {

    private Long id;

    private String name;

    private String code;


    private Integer type;


    private Integer indexArticle;


    private Integer totalTime;

    private Integer numPerson;

    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    private java.sql.Timestamp deletedAt;

    private Integer openAccess;

    private Integer openAccessScopus;

    private Integer yearId;

}
