package com.cineLog.cineLog.controllerV2;


import com.cineLog.cineLog.entity.GenreEntity;
import com.cineLog.cineLog.service.GenreEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Genre")
public class GenreEntityController {

    @Autowired
    private GenreEntryService genreEntryService;


    @GetMapping
    public List<GenreEntity> getAll() {
        return genreEntryService.getAll();
    }

    @GetMapping("id/{myId}")
    public GenreEntity getEntryById(@PathVariable String myId) {
        return genreEntryService.findById(myId).orElse(null);
    }


    @PostMapping
    public GenreEntity createEntry(@RequestBody GenreEntity genreEntity) {
        genreEntryService.saveEntry(genreEntity);
        return genreEntity;
    }


    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable String myId) {
        genreEntryService.deleteById(myId);
        return true;

    }

    @PutMapping("id/{myId}")
    public GenreEntity updateById(@PathVariable String myId, @RequestBody GenreEntity newEntry) {


        GenreEntity old = genreEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().equals("") ? newEntry.getDescription() : old.getDescription());
            old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
        }
        genreEntryService.saveEntry(old);
        return old;
    }
}
