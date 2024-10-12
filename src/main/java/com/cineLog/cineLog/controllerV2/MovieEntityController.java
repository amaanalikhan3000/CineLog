package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.GenreEntity;
import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.service.MovieEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieEntityController {

    @Autowired
    private MovieEntryService movieEntryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MovieEntity> all = movieEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return  new ResponseEntity<>(all,HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        Optional<MovieEntity> movieEntity = movieEntryService.findById(String.valueOf(myId));
        if(movieEntity.isPresent()){
            return  new ResponseEntity<>(movieEntity, HttpStatus.OK);
        }
        return  new ResponseEntity<>(movieEntity, HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<MovieEntity> createEntry(@RequestBody MovieEntity movieEntity) {
        try {
            movieEntryService.saveEntry(movieEntity);
            return new ResponseEntity<>(movieEntity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<MovieEntity> deleteById(@PathVariable ObjectId myId) {
        movieEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myId, @RequestBody MovieEntity newEntry) {
        MovieEntity old = movieEntryService.findById(String.valueOf(myId)).orElse(null);
        if (old != null) {
            old.setGenres(newEntry.getGenres() != null && !newEntry.getGenres().isEmpty() ? newEntry.getGenres() : old.getGenres());
            old.setTags(newEntry.getTags() != null && !newEntry.getTags().isEmpty() ? newEntry.getTags() : old.getTags());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setReleaseDate(newEntry.getReleaseDate() != null ? newEntry.getReleaseDate() : old.getReleaseDate());

            movieEntryService.saveEntry(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
