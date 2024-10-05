package com.cineLog.cineLog.controller;


import com.cineLog.cineLog.entity.GenreEntity;
import com.cineLog.cineLog.entity.ReviewEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Genre")
public class GenreEntityController {
    private Map<String, GenreEntity> genreEntityMap = new HashMap<>();



    @GetMapping
    public List<GenreEntity> getAll(){
        return new ArrayList<>(genreEntityMap.values());
    }

    @GetMapping("id/{myId}")
    public GenreEntity getEntryById(@PathVariable String myId){
        return genreEntityMap.get(myId);
    }

    @PostMapping
    public boolean createEntry(@RequestBody GenreEntity genreEntity){
        genreEntityMap.put(genreEntity.getId(),genreEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable String myId){
        genreEntityMap.remove(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public GenreEntity updateById(@PathVariable String myId, @RequestBody GenreEntity genreEntity){
        return genreEntityMap.put(myId,genreEntity);
    }
}
