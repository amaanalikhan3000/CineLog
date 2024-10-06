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
    public ResponseEntity<List<UserEntity>> getAll() {
        List<UserEntity> users = userEntryService.getAll();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<UserEntity> getEntryById(@PathVariable ObjectId myId) {
        Optional<UserEntity> userEntity = userEntryService.findById(myId);
        return userEntity.map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserEntity> createEntry(@RequestBody UserEntity userEntity) {
        userEntryService.saveEntry(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
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

            userEntryService.saveEntry(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
