package com.cineLog.cineLog.repository;

import com.cineLog.cineLog.entity.UserEntity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntityRepo extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByusername(String username);


}
