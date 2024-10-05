package com.cineLog.cineLog.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "moviesEntities")
@Getter
@Setter
public class MovieEntity {
    @Id
    private ObjectId id;
    private String title;
    private Date releaseDate;
    private List<String> genres;
    private List<String> tags;

    // Getters and setters
}
