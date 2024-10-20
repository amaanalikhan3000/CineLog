package com.cineLog.cineLog.repository;

import com.cineLog.cineLog.entity.GenreEntity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreEntryRepo extends MongoRepository<GenreEntity,String> {
    GenreEntity findByname(String name);
}
