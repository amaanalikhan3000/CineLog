package com.cineLog.cineLog.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
// OPTIONAL
@Document(collection = "GenreEntity")
public class GenreEntity {


    @Id
    private String id;
    private String name;
    private String description;


}
