package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.GenreEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.GenreEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class GenreEntryService {
    @Autowired
    private GenreEntryRepo genreEntryRepo;


    public void saveEntry(GenreEntity genreEntity){
        genreEntryRepo.save(genreEntity);
    }

    public List<GenreEntity> getAll(){
        return genreEntryRepo.findAll();
    }

    public Optional<GenreEntity> findById(String id){
        return genreEntryRepo.findById(id);
    }

    public void deleteById(String id){
        genreEntryRepo.deleteById(id);
    }


    public GenreEntity findByname(String name){
        return genreEntryRepo.findByname(name);
    }

}
