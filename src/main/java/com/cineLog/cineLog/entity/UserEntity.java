package com.cineLog.cineLog.entity;

import com.cineLog.cineLog.controller.UserEntityController;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserEntity extends UserEntityController {
    private ObjectId userId;
    private String username;
    private String email;
    private String profilepic;
    private List<String> favorites;
    private List<String> watchlist;
    private Date createdAt;
    private Date updatedAt;
}

