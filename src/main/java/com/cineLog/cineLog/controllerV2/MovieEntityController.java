package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.service.MovieEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieEntityController {

    @Autowired
    private MovieEntryService movieEntryService;

    @GetMapping
    public List<MovieEntity> getAll() {
        return movieEntryService.getAll();
    }

    @GetMapping("id/{myId}")
    public MovieEntity getEntryById(@PathVariable ObjectId myId) {
        return movieEntryService.findById(myId).orElse(null);
    }

    @PostMapping
    public boolean createEntry(@RequestBody MovieEntity movieEntity) {
        movieEntryService.saveEntry(movieEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId) {
        movieEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public MovieEntity updateById(@PathVariable ObjectId myId, @RequestBody MovieEntity newEntry) {
        MovieEntity old = movieEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setGenres(newEntry.getGenres() != null && !newEntry.getGenres().isEmpty() ? newEntry.getGenres() : old.getGenres());
            old.setTags(newEntry.getTags() != null && !newEntry.getTags().isEmpty() ? newEntry.getTags() : old.getTags());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setReleaseDate(newEntry.getReleaseDate() != null ? newEntry.getReleaseDate() : old.getReleaseDate());
        }
        movieEntryService.saveEntry(old);
        return old;
    }
}
