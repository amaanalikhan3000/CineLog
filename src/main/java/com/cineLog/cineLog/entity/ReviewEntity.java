package com.cineLog.cineLog.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "reviewEntity")
public class ReviewEntity {

    @Id
    private String id;
    private String userId; // Reference to the User
    private String movieId; // Reference to the Movie
    private double rating; // Consider using Double for ratings
    private String review;
    private Date dateWatched;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
