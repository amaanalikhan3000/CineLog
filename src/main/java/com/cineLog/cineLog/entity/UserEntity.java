package com.cineLog.cineLog.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "userEntity")
public class UserEntity {
    @Id
    private ObjectId userId;
    private String username;
    private String email;
    private String profilepic;
    private List<String> favorites;
    private List<String> watchlist;
    private Date createdAt;
    private Date updatedAt;
}
