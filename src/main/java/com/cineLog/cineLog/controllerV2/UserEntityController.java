package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntityController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserEntity> users = userEntryService.getAll();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createEntry(@RequestBody UserEntity userEntity) {
        userEntryService.saveEntry(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<UserEntity> getEntryById(@PathVariable ObjectId myId) {
        Optional<UserEntity> userEntity = userEntryService.findById(myId);
        return userEntity.map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId myId) {
        Optional<UserEntity> userEntity = userEntryService.findById(myId);
        if (userEntity.isPresent()) {
            userEntryService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<UserEntity> updateById(@PathVariable ObjectId myId, @RequestBody UserEntity newEntry) {
        Optional<UserEntity> oldUserOptional = userEntryService.findById(myId);
        if (oldUserOptional.isPresent()) {
            UserEntity oldUser = oldUserOptional.get();
            oldUser.setUsername(newEntry.getUsername() != null && !newEntry.getUsername().isEmpty() ? newEntry.getUsername() : oldUser.getUsername());
            oldUser.setEmail(newEntry.getEmail() != null && !newEntry.getEmail().isEmpty() ? newEntry.getEmail() : oldUser.getEmail());
            oldUser.setProfilepic(newEntry.getProfilepic() != null && !newEntry.getProfilepic().isEmpty() ? newEntry.getProfilepic() : oldUser.getProfilepic());
            oldUser.setFavorites(newEntry.getFavorites() != null && !newEntry.getFavorites().isEmpty() ? newEntry.getFavorites() : oldUser.getFavorites());
            oldUser.setWatchlist(newEntry.getWatchlist() != null && !newEntry.getWatchlist().isEmpty() ? newEntry.getWatchlist() : oldUser.getWatchlist());
            oldUser.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : oldUser.getCreatedAt());
            oldUser.setUpdatedAt(newEntry.getUpdatedAt() != null ? newEntry.getUpdatedAt() : oldUser.getUpdatedAt());
            oldUser.setPassword(newEntry.getPassword()!=null ? newEntry.getPassword():oldUser.getPassword());
            userEntryService.saveEntry(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping("userName/{username}")
    public ResponseEntity<UserEntity> updateById(@PathVariable String  username, @RequestBody UserEntity newEntry) {
        Optional<UserEntity> oldUserOptional = Optional.ofNullable(userEntryService.findByusername(username));
        if (oldUserOptional.isPresent()) {
            UserEntity oldUser = oldUserOptional.get();
            oldUser.setUsername(newEntry.getUsername() != null && !newEntry.getUsername().isEmpty() ? newEntry.getUsername() : oldUser.getUsername());
            oldUser.setEmail(newEntry.getEmail() != null && !newEntry.getEmail().isEmpty() ? newEntry.getEmail() : oldUser.getEmail());
            oldUser.setProfilepic(newEntry.getProfilepic() != null && !newEntry.getProfilepic().isEmpty() ? newEntry.getProfilepic() : oldUser.getProfilepic());
            oldUser.setFavorites(newEntry.getFavorites() != null && !newEntry.getFavorites().isEmpty() ? newEntry.getFavorites() : oldUser.getFavorites());
            oldUser.setWatchlist(newEntry.getWatchlist() != null && !newEntry.getWatchlist().isEmpty() ? newEntry.getWatchlist() : oldUser.getWatchlist());
            oldUser.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : oldUser.getCreatedAt());
            oldUser.setUpdatedAt(newEntry.getUpdatedAt() != null ? newEntry.getUpdatedAt() : oldUser.getUpdatedAt());
            oldUser.setPassword(newEntry.getPassword()!=null ? newEntry.getPassword():oldUser.getPassword());
            userEntryService.saveEntry(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);



    }


    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity, @PathVariable String username){

        UserEntity userInDb = userEntryService.findByusername(userEntity.getUsername());
        if(userInDb!=null){
            userInDb.setUsername(userEntity.getUsername());
            userInDb.setPassword(userEntity.getPassword());
            userEntryService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
        Many to many relationship
        A user can have many favorite movies, and a movie can be favorited by many users.

     */

    @PostMapping("/{userId}/favorites/{movieId}")
    public ResponseEntity<?> addToFavorites(@PathVariable ObjectId userId, @PathVariable String movieId) {
        try {
            userEntryService.addToFavorites(userId, movieId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/favorites/{movieId}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable ObjectId userId, @PathVariable String movieId) {
        try {
            userEntryService.removeFromFavorites(userId, movieId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<?> getFavourites(@PathVariable ObjectId userId){
        UserEntity user = userEntryService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseEntity<>(user.getFavorites(), HttpStatus.OK);
    }


    /*
        A user can have multiple movies in their watchlist, and a movie can appear in the
        watchlist of multiple users.
     */

    @PostMapping("/{userId}/watchlist/{movieId}")
    public ResponseEntity<?> addToWatchlist(@PathVariable ObjectId userId, @PathVariable String movieId) {
        try {
            userEntryService.addTowatchList(userId, movieId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/watchlist/{movieId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable ObjectId userId, @PathVariable String movieId) {
        try {
            userEntryService.removeFromWatchlist(userId, movieId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/watchList")
    public ResponseEntity<?> getWatchlist(@PathVariable ObjectId userId){
        UserEntity user = userEntryService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseEntity<>(user.getWatchlist(), HttpStatus.OK);
    }



}
