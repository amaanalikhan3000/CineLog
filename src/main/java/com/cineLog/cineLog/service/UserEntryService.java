package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.UserEntityRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserEntryService {
    @Autowired
    private UserEntityRepo userEntityRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userEntityRepo.save(userEntity);
    }

//    public void saveNewUser(UserEntity userEntity) {
//        userEntityRepo.save(userEntity);
//    }


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
        A user can have many favorite movies, and a movie can be selected as
         favorite by many users.
     */


    @Transactional
    public void addToFavorites(ObjectId userId, String movieId) {
        try {
            UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            if (!user.getFavorites().contains(movieId)) {
                user.getFavorites().add(movieId);
                saveEntry(user);
            } else {
                throw new RuntimeException("Movie already in favorites");
            }

        }
        catch (Exception e){
           throw new RuntimeException("An error occurred while adding to favorites",e);
        }

    }

    @Transactional
    public void addTowatchList(ObjectId userId, String movieId) {
        try{
        UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getWatchlist().contains(movieId)) {
            user.getWatchlist().add(movieId);
            saveEntry(user);
        } else {
            throw new RuntimeException("Movie already in watchlist");
        }}catch (Exception e){
            throw new RuntimeException("An error occurred while adding to watchlist",e);
        }
    }

    @Transactional
    public void removeFromFavorites(ObjectId userId,String movieId){
        try {
            UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            if(user.getFavorites().remove(movieId)){
                saveEntry(user);
            }else {
                throw new RuntimeException("Movie not found in favorites");
            }
        }catch (Exception e){
            throw new RuntimeException("An error occurred while removing from favorites",e);
        }


    }

    @Transactional
    public void removeFromWatchlist(ObjectId userId, String movieId) {
        try{
        UserEntity user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getWatchlist().remove(movieId)){
            saveEntry(user);
        }else{
            throw new RuntimeException("Movie not found in favorites");
        }}
        catch (Exception e){
            throw new RuntimeException("An error occurred while removing from watchlist",e);
        }
    }
}
