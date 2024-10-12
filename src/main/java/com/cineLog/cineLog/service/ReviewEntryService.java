package com.cineLog.cineLog.service;




import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.ReviewEntityRepo;

import com.cineLog.cineLog.repository.UserEntityRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewEntryService {

    @Autowired
    private ReviewEntityRepo reviewEntityRepo;

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Autowired
    private UserEntryService userEntryService;



    public List<ReviewEntity> getAll() {
        return reviewEntityRepo.findAll();
    }


    public Optional<ReviewEntity> findById(String id){
        return reviewEntityRepo.findById(id);
    }



    public void saveEntry(ReviewEntity reviewEntity, String username) {
        // Fetch the user by username
        UserEntity user = userEntryService.findByusername(username);
        reviewEntity.setCreatedAt(LocalDateTime.now());
        ReviewEntity saved = reviewEntityRepo.save(reviewEntity);
        user.getReviewEntities().add(saved);
        userEntryService.saveEntry(user);
    }

    public void saveEntry(ReviewEntity reviewEntity) {
        reviewEntityRepo.save(reviewEntity);
    }

    public List<ReviewEntity> findByMovieId(String movieId) {
        return reviewEntityRepo.findByMovieId(movieId);
    }



    public void deleteById(String id, String username) {
        UserEntity user = userEntryService.findByusername(username);
        user.getReviewEntities().removeIf(x->x.getId().equals(id));
        userEntryService.saveEntry(user);
        reviewEntityRepo.deleteById(id);
    }
}
