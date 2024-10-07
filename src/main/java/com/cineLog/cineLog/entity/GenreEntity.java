package com.cineLog.cineLog.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
// OPTIONAL
@Document(collection = "GenreEntity")
public class GenreEntity {


    @Id
    private String id;
    private String name;
    private String description;


}
