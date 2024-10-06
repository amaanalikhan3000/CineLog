package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.service.ReviewEntryService;
import org.bson.types.ObjectId;
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

    @GetMapping
    public ResponseEntity<List<ReviewEntity>> getAll() {
        List<ReviewEntity> reviews = reviewEntryService.getAll();
        if (reviews != null && !reviews.isEmpty()) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<ReviewEntity> getEntryById(@PathVariable String myId) {
        Optional<ReviewEntity> reviewEntity = reviewEntryService.findById(myId);
        return reviewEntity.map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ReviewEntity> createEntry(@RequestBody ReviewEntity reviewEntity) {
        reviewEntity.setCreatedAt(LocalDateTime.now());
        reviewEntryService.saveEntry(reviewEntity);
        return new ResponseEntity<>(reviewEntity, HttpStatus.CREATED);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable String myId) {
        Optional<ReviewEntity> reviewEntity = reviewEntryService.findById(myId);
        if (reviewEntity.isPresent()) {
            reviewEntryService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<ReviewEntity> updateById(@PathVariable String myId, @RequestBody ReviewEntity newEntry) {
        Optional<ReviewEntity> oldReviewOptional = reviewEntryService.findById(myId);
        if (oldReviewOptional.isPresent()) {
            ReviewEntity old = oldReviewOptional.get();
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
