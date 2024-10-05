package com.cineLog.cineLog.controller;

import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")

public class MovieEntityController {
    private Map<ObjectId, MovieEntity> movieEntityMap = new HashMap<>();

    @GetMapping
    public List<MovieEntityController> getAll(){
        return new ArrayList<>(movieEntityMap.values());
    }

    @GetMapping("id/{myId}")
    public MovieEntityController getEntryById(@PathVariable ObjectId myId){
        return movieEntityMap.get(myId);
    }


    @PostMapping
    public boolean createEntry(@RequestBody MovieEntity movieEntity){
        movieEntityMap.put(movieEntity.getId(),movieEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId){
        movieEntityMap.remove(myId);
        return true;
    }


    @PutMapping("id/{myId}")
    public MovieEntity updateById(@PathVariable ObjectId myId, @RequestBody MovieEntity movieEntity){
        return movieEntityMap.put(myId, movieEntity);
    }


}
