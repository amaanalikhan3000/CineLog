package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.MovieEntity;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.MovieEntryService;
import com.cineLog.cineLog.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/movie")
public class MovieEntityController {

    @Autowired
    private MovieEntryService movieEntryService;


    @Autowired
    private UserEntryService userEntryService;


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
//        List<MovieEntity> all = movieEntryService.getAll();
//        if(all!=null && !all.isEmpty()){
//            return new ResponseEntity<>(all,HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(all,HttpStatus.NOT_FOUND);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userEntryService.findByusername(userName);
        List<MovieEntity> all = movieEntryService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        Optional<MovieEntity> movieEntity = movieEntryService.findById(String.valueOf(myId));
        if(movieEntity.isPresent()){
            return  new ResponseEntity<>(movieEntity, HttpStatus.OK);
        }
        return  new ResponseEntity<>(movieEntity, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MovieEntity> createEntry(@RequestBody MovieEntity movieEntity) {
        try {
            movieEntryService.saveEntry(movieEntity);
            return new ResponseEntity<>(movieEntity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("id/{myId}")
    public ResponseEntity<MovieEntity> deleteById(@PathVariable ObjectId myId) {
        movieEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//        boolean removedCheck = movieEntryService.deleteById(myId, userName);
//        if(removedCheck){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

    }


    @PreAuthorize("hasRole('ADMIN')")
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
