package com.cineLog.cineLog.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReviewEntity {

    private String id;
    private String userId; // Reference to the User
    private String movieId; // Reference to the Movie
    private double rating; // Consider using Double for ratings
    private String review;
    private Date dateWatched;
    private Date createdAt;
    private Date updatedAt;

}
