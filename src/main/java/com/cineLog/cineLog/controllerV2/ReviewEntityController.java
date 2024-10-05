package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.entity.ReviewEntity;
import com.cineLog.cineLog.service.ReviewEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewEntityController {
    @Autowired
    ReviewEntryService reviewEntryService;

    @GetMapping
    public List<ReviewEntity> getAll(){
        return reviewEntryService.getAll();
    }

    @GetMapping("id/{myId}")
    public ReviewEntity getEntryById(@PathVariable String myId) {
        return reviewEntryService.findById(myId).orElse(null);
    }

    @PostMapping
    public boolean createEntry(@RequestBody ReviewEntity reviewEntity){
        reviewEntity.setCreatedAt(LocalDateTime.now());
        reviewEntryService.saveEntry(reviewEntity);
        return true;
    }



    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable String myId){
        reviewEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public ReviewEntity updateById(@PathVariable String myId, @RequestBody ReviewEntity newEntry){

        ReviewEntity old = reviewEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setUserId(newEntry.getUserId() != null && !newEntry.getUserId().isEmpty() ? newEntry.getUserId() : old.getUserId());
            old.setMovieId(newEntry.getMovieId() != null && !newEntry.getMovieId().isEmpty() ? newEntry.getMovieId() : old.getMovieId());
            old.setRating(newEntry.getRating() != 0 ? newEntry.getRating() : old.getRating()); // Assuming rating is 0 if not updated
            old.setReview(newEntry.getReview() != null && !newEntry.getReview().isEmpty() ? newEntry.getReview() : old.getReview());
            old.setDateWatched(newEntry.getDateWatched() != null ? newEntry.getDateWatched() : old.getDateWatched());
            old.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : old.getCreatedAt());
            old.setUpdatedAt(LocalDateTime.now()); // Automatically update the 'updatedAt' field to the current time
        }

        reviewEntryService.saveEntry(old);
        return old;
    }
}
