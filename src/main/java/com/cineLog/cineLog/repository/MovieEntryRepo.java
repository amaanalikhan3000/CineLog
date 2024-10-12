package com.cineLog.cineLog.repository;

import com.cineLog.cineLog.entity.MovieEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieEntryRepo extends MongoRepository<MovieEntity, ObjectId> {
    Optional<MovieEntity> findById(String id);
}
