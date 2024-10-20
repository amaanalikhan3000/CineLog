package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.MovieEntity;
import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.MovieEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieEntryService {


    @Autowired
    private MovieEntryRepo movieEntryRepo;

    @Autowired
    private  UserEntryService userEntryService;

    public void saveEntry(MovieEntity movieEntity) {
        movieEntryRepo.save(movieEntity);
    }

    public List<MovieEntity> getAll() {
        return movieEntryRepo.findAll();
    }

    public Optional<MovieEntity> findById(String id) {
        return movieEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        movieEntryRepo.deleteById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        boolean removed = false;

        try {
            UserEntity user = userEntryService.findByusername(username);
            removed = user.getReviewEntities().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userEntryService.saveEntry(user);
                movieEntryRepo.deleteById(id);
            }


        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry", e);
        }
        return removed;
    }
}
