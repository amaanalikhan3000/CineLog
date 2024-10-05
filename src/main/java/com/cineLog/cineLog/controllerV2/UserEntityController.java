package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntityController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public List<UserEntity> getAll() {
        return userEntryService.getAll();
    }

    @GetMapping("id/{myId}")
    public UserEntity getEntryById(@PathVariable ObjectId myId) {
        return userEntryService.findById(myId).orElse(null);
    }

    @PostMapping
    public boolean createEntry(@RequestBody UserEntity userEntity) {
        userEntryService.saveEntry(userEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId) {
        userEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public UserEntity updateById(@PathVariable ObjectId myId, @RequestBody UserEntity newEntry) {
        UserEntity old = userEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setUsername(newEntry.getUsername() != null && !newEntry.getUsername().isEmpty() ? newEntry.getUsername() : old.getUsername());
            old.setEmail(newEntry.getEmail() != null && !newEntry.getEmail().isEmpty() ? newEntry.getEmail() : old.getEmail());
            old.setProfilepic(newEntry.getProfilepic() != null && !newEntry.getProfilepic().isEmpty() ? newEntry.getProfilepic() : old.getProfilepic());
            old.setFavorites(newEntry.getFavorites() != null && !newEntry.getFavorites().isEmpty() ? newEntry.getFavorites() : old.getFavorites());
            old.setWatchlist(newEntry.getWatchlist() != null && !newEntry.getWatchlist().isEmpty() ? newEntry.getWatchlist() : old.getWatchlist());
            old.setCreatedAt(newEntry.getCreatedAt() != null ? newEntry.getCreatedAt() : old.getCreatedAt());
            old.setUpdatedAt(newEntry.getUpdatedAt() != null ? newEntry.getUpdatedAt() : old.getUpdatedAt());
        }
        userEntryService.saveEntry(old);
        return old;
    }
}
