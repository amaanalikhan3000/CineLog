package com.cineLog.cineLog.service;




import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.ReviewEntityRepo;

import com.cineLog.cineLog.repository.UserEntityRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
//        UserEntity user = userEntryService.findByusername(username);
//
//        // If user is found, proceed
//        if (user != null) {
//            // Set timestamps and save the review
//            reviewEntity.setCreatedAt(LocalDateTime.now());
//            ReviewEntity saved = reviewEntityRepo.save(reviewEntity);
//
//            // Initialize the reviewEntities list if null
//            if (user.getReviewEntities() == null) {
//                user.setReviewEntities(new ArrayList<>()); // Initialize list if it's null
//            }
//
//            // Add the saved review to the user's reviewEntities
//            user.getReviewEntities().add(saved);
//
//            // Save the user with the updated review list
//            userEntryService.saveEntry(user);
//        } else {
//            throw new IllegalArgumentException("User not found with username: " + username);
//        }

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
