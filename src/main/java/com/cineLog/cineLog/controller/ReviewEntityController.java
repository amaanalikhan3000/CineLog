package com.cineLog.cineLog.controller;

import com.cineLog.cineLog.entity.ReviewEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewEntityController {
    private Map<String, ReviewEntity> reviewEntityMap = new HashMap<>();



    @GetMapping
    public List<ReviewEntity> getAll(){
        return new ArrayList<>(reviewEntityMap.values());
    }

    @GetMapping("id/{myId}")
    public ReviewEntity getEntryById(@PathVariable String myId){
        return reviewEntityMap.get(myId);
    }

    @PostMapping
    public boolean createEntry(@RequestBody ReviewEntity reviewEntity){
        reviewEntityMap.put(reviewEntity.getUserId(),reviewEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable String myId){
        reviewEntityMap.remove(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public ReviewEntity updateById(@PathVariable String myId, @RequestBody ReviewEntity reviewEntity){
        return reviewEntityMap.put(myId,reviewEntity);
    }
}
