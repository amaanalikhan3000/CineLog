package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.repository.MovieEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieEntryService {


    @Autowired
    private MovieEntryRepo movieEntryRepo;

    public void saveEntry(MovieEntity movieEntity) {
        movieEntryRepo.save(movieEntity);
    }

    public List<MovieEntity> getAll() {
        return movieEntryRepo.findAll();
    }

    public Optional<MovieEntity> findById(ObjectId id) {
        return movieEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        movieEntryRepo.deleteById(id);
    }
}
