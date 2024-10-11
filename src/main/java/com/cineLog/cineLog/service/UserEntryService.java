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



}
