package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.UserEntityRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserEntryService {
    @Autowired
    private UserEntityRepo userEntityRepo;

    public void saveEntry(UserEntity userEntity) {
        userEntityRepo.save(userEntity);
    }


    public List<UserEntity> getAll(){
        return userEntityRepo.findAll();
    }

    public Optional<UserEntity> findById(ObjectId userId){
        return userEntityRepo.findById(userId);
    }

    public UserEntity findByusername(String username){
        return userEntityRepo.findByusername(username);
    }


    public void deleteById(ObjectId userId) {
        userEntityRepo.deleteById(userId);
    }




    /*
        A user can have many favorite movies, and a movie can be favorited by many users.
     */

    public void addToFavorites(ObjectId userId, String movieId) {
        UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getFavorites().contains(movieId)) {
            user.getFavorites().add(movieId);
            saveEntry(user);
        } else {
            throw new RuntimeException("Movie already in favorites");
        }
    }

    public void addTowatchList(ObjectId userId, String movieId) {
        UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getWatchlist().contains(movieId)) {
            user.getWatchlist().add(movieId);
            saveEntry(user);
        } else {
            throw new RuntimeException("Movie already in watchlist");
        }
    }

    public void removeFromFavorites(ObjectId userId,String movieId){
        UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getFavorites().remove(movieId)){
            saveEntry(user);
        }else{
            throw new RuntimeException("Movie not found in favorites");
        }

    }

    public void removeFromWatchlist(ObjectId userId, String movieId) {
        UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getWatchlist().remove(movieId)){
            saveEntry(user);
        }else{
            throw new RuntimeException("Movie not found in favorites");
        }
    }
}
