package com.cineLog.cineLog.repository;

import com.cineLog.cineLog.entity.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewEntityRepo extends MongoRepository<ReviewEntity,String> {

    List<ReviewEntity> findByMovieId(String movieId);
}
