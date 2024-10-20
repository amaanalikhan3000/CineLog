package com.cineLog.cineLog.controllerV2;


import com.cineLog.cineLog.entity.GenreEntity;
import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.GenreEntryService;
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
@RequestMapping("/Genre")
public class GenreEntityController {

    @Autowired
    private GenreEntryService genreEntryService;


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
//        List<GenreEntity> all = genreEntryService.getAll();
//        if(all!=null && !all.isEmpty()){
//            return new ResponseEntity<>(all,HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(all,HttpStatus.NOT_FOUND);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        GenreEntity user = genreEntryService.findByname(name);
        List<GenreEntity> all = genreEntryService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable String myId) {
        Optional<GenreEntity> genreEntity = genreEntryService.findById(myId);
        // return genreEntity.map(entity -> new ResponseEntity<>(entity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(genreEntity.get(), HttpStatus.NOT_FOUND));
        if (genreEntity.isPresent()) {
            return new ResponseEntity<>(genreEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(genreEntity, HttpStatus.NOT_FOUND);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GenreEntity> createEntry(@RequestBody GenreEntity genreEntity) {
        try {
            genreEntryService.saveEntry(genreEntity);
            return new ResponseEntity<>(genreEntity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId) {
        genreEntryService.deleteById(myId);
        return new ResponseEntity<>(genreEntryService, HttpStatus.NO_CONTENT);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateById(@PathVariable String myId, @RequestBody GenreEntity newEntry) {


        GenreEntity old = genreEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().equals("") ? newEntry.getDescription() : old.getDescription());
            old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
            genreEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
