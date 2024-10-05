package com.cineLog.cineLog.service;



import com.cineLog.cineLog.entity.GenreEntity;
import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.repository.ReviewEntityRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewEntryService {

    @Autowired
    ReviewEntityRepo reviewEntityRepo;



    public List<ReviewEntity> getAll() {
        return reviewEntityRepo.findAll();
    }


    public Optional<ReviewEntity> findById(String id){
        return reviewEntityRepo.findById(id);
    }
    public void saveEntry(ReviewEntity reviewEntity) {
        reviewEntityRepo.save(reviewEntity);
    }

    public void deleteById(String id) {
        reviewEntityRepo.deleteById(id);
    }
}
