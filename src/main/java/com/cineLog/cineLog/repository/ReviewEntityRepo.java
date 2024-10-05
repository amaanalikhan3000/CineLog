package com.cineLog.cineLog.repository;

import com.cineLog.cineLog.entity.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewEntityRepo extends MongoRepository<ReviewEntity,String> {
}
