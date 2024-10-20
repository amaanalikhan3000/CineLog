package com.cineLog.cineLog.controllerV2;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUser() {
        List<UserEntity> all = userEntryService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);

    }

    @PostMapping("create-admin-user")
    public void createUser(@RequestBody UserEntity user){
        userEntryService.saveAdmin(user);
    }
}
