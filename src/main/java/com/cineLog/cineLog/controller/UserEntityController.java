package com.cineLog.cineLog.controller;


import com.cineLog.cineLog.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserEntityController {
    private Map<ObjectId,UserEntity> userEntityMap = new HashMap<>();

    @GetMapping
    public List<UserEntity> getAll(){
        return new ArrayList<>(userEntityMap.values());
    }

    @GetMapping("id/{myId}")
    public UserEntity getEntryById(@PathVariable ObjectId myId){
        return userEntityMap.get(myId);
    }

    @PostMapping
    public boolean createEntry(@RequestBody UserEntity userEntity){
        userEntityMap.put(userEntity.getUserId(),userEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId){
        userEntityMap.remove(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public UserEntity updateById(@PathVariable ObjectId myId, @RequestBody UserEntity userEntity){
       return userEntityMap.put(myId, userEntity);
    }



}
