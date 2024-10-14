package com.cineLog.cineLog.controllerV2;


import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("abc")
    public String hello(){
        return "ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserEntity> createEntry(@RequestBody UserEntity userEntity) {
        userEntryService.saveEntry(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

}
