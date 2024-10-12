package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.MovieEntryService;
import com.cineLog.cineLog.service.ReviewEntryService;
import com.cineLog.cineLog.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewEntityController {

    @Autowired
    private ReviewEntryService reviewEntryService;

    @Autowired
    private UserEntryService userEntryService;


    @Autowired
    private MovieEntryService movieEntryService;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllReviewsOfUser(@PathVariable String username) {
        UserEntity user = userEntryService.findByusername(username);
        List<ReviewEntity> all = user.getReviewEntities();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getAllReviewsByMovie(@PathVariable String movieId) {
        // Fetch reviews by movieId
        List<ReviewEntity> reviews = reviewEntryService.findByMovieId(movieId);
        if (!reviews.isEmpty()) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/movie/{movieId}")
    public ResponseEntity<ReviewEntity> createReviewForMovie(
            @RequestBody ReviewEntity reviewEntity,
            @PathVariable String movieId) {

        reviewEntity.setMovieId(movieId);
        reviewEntryService.saveEntry(reviewEntity);
        return new ResponseEntity<>(reviewEntity, HttpStatus.CREATED);
    }



    @PostMapping("{username}")
    public ResponseEntity<ReviewEntity> createEntry(@RequestBody ReviewEntity reviewEntity, @PathVariable String username) {
        try {


            reviewEntryService.saveEntry(reviewEntity,username);
            return new ResponseEntity<>(reviewEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable String myId) {
        Optional<ReviewEntity> reviewEntity = reviewEntryService.findById(myId);

        if(reviewEntity.isPresent()){
            return new ResponseEntity<>(reviewEntity, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId,@PathVariable String username) {

            reviewEntryService.deleteById(myId,username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

//    @PutMapping("id/{username}/{myId}")
//    public ResponseEntity<ReviewEntity> updateById(
//            @PathVariable String myId,
//            @RequestBody ReviewEntity newEntry,
//            @PathVariable String username
//    ) {
//        Optional<ReviewEntity> oldReviewOptional = reviewEntryService.findById(myId);
//        if (oldReviewOptional.isPresent()) {
//            ReviewEntity old = oldReviewOptional.get();
//            old.setUserId(newEntry.getUserId() != null && !newEntry.getUserId().isEmpty() ? newEntry.getUserId() : old.getUserId());
//            old.setMovieId(newEntry.getMovieId() != null && !newEntry.getMovieId().isEmpty() ? newEntry.getMovieId() : old.getMovieId());
//            old.setRating(newEntry.getRating() != 0 ? newEntry.getRating() : old.getRating()); // Assuming rating is 0 if not updated
//            old.setReview(newEntry.getReview() != null && !newEntry.getReview().isEmpty() ? newEntry.getReview() : old.getReview());
//            old.setDateWatched(newEntry.getDateWatched() != null ? newEntry.getDateWatched() : old.getDateWatched());
//            old.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : old.getCreatedAt());
//            old.setUpdatedAt(LocalDateTime.now()); // Automatically update the 'updatedAt' field to the current time
//
//            reviewEntryService.saveEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("id/{username}/{myId}")
    public ResponseEntity<ReviewEntity> updateById(
            @PathVariable String myId,
            @RequestBody ReviewEntity newEntry,
            @PathVariable String username
    ) {
        ReviewEntity old = reviewEntryService.findById(myId).orElse(null);
        if (old!=null) {
            old.setUserId(newEntry.getUserId() != null && !newEntry.getUserId().isEmpty() ? newEntry.getUserId() : old.getUserId());
            old.setMovieId(newEntry.getMovieId() != null && !newEntry.getMovieId().isEmpty() ? newEntry.getMovieId() : old.getMovieId());
            old.setRating(newEntry.getRating() != 0 ? newEntry.getRating() : old.getRating()); // Assuming rating is 0 if not updated
            old.setReview(newEntry.getReview() != null && !newEntry.getReview().isEmpty() ? newEntry.getReview() : old.getReview());
            old.setDateWatched(newEntry.getDateWatched() != null ? newEntry.getDateWatched() : old.getDateWatched());
            old.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : old.getCreatedAt());
            old.setUpdatedAt(LocalDateTime.now()); // Automatically update the 'updatedAt' field to the current time

            reviewEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
