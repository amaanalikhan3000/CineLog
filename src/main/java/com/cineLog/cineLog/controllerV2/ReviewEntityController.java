package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.MovieEntryService;
import com.cineLog.cineLog.service.ReviewEntryService;
import com.cineLog.cineLog.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
public class ReviewEntityController {

    @Autowired
    private ReviewEntryService reviewEntryService;

    @Autowired
    private UserEntryService userEntryService;


    @Autowired
    private MovieEntryService movieEntryService;

    @GetMapping
    public ResponseEntity<?> getAllReviewsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userEntryService.findByusername(userName);
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
        reviewEntryService.saveNewUser(reviewEntity);
        return new ResponseEntity<>(reviewEntity, HttpStatus.CREATED);
    }


    @PostMapping
    public ResponseEntity<ReviewEntity> createEntry(@RequestBody ReviewEntity reviewEntity) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            reviewEntryService.saveNewUser(reviewEntity, userName);
            return new ResponseEntity<>(reviewEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userEntryService.findByusername(userName);

        List<ReviewEntity> collect = user.getReviewEntities()
                    .stream()
                    .filter(x -> x.getId()
                            .equals(myId))
                    .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<ReviewEntity> reviewEntity = reviewEntryService.findById(myId);
            if (reviewEntity.isPresent()) {
                return new ResponseEntity<>(reviewEntity, HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removedCheck = reviewEntryService.deleteById(myId, userName);
        if(removedCheck){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("id/{myId}")
    public ResponseEntity<ReviewEntity> updateById(
            @PathVariable String myId,
            @RequestBody ReviewEntity newEntry
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userEntryService.findByusername(userName);

        List<ReviewEntity> collect = user.getReviewEntities()
                .stream()
                .filter(x -> x.getId()
                        .equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<ReviewEntity> reviewEntity = reviewEntryService.findById(myId);
            if (reviewEntity.isPresent()) {
                ReviewEntity reviewEntity2 = reviewEntryService.findById(myId).orElse(null);

                if (reviewEntity2 != null) {
                    reviewEntity2.setUserId(newEntry.getUserId() != null && !newEntry.getUserId().isEmpty() ? newEntry.getUserId() : reviewEntity2.getUserId());
                    reviewEntity2.setMovieId(newEntry.getMovieId() != null && !newEntry.getMovieId().isEmpty() ? newEntry.getMovieId() : reviewEntity2.getMovieId());
                    reviewEntity2.setRating(newEntry.getRating() != 0 ? newEntry.getRating() : reviewEntity2.getRating()); // Assuming rating is 0 if not updated
                    reviewEntity2.setReview(newEntry.getReview() != null && !newEntry.getReview().isEmpty() ? newEntry.getReview() : reviewEntity2.getReview());
                    reviewEntity2.setDateWatched(newEntry.getDateWatched() != null ? newEntry.getDateWatched() : reviewEntity2.getDateWatched());
                    reviewEntity2.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : reviewEntity2.getCreatedAt());
                    reviewEntity2.setUpdatedAt(LocalDateTime.now()); // Automatically update the 'updatedAt' field to the current time

                    reviewEntryService.saveNewUser(reviewEntity2);
                    return new ResponseEntity<>(reviewEntity2, HttpStatus.OK);
                }

            }
        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
