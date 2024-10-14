package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class customUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepo.findByusername(username);
        if(user!=null){
            /* An alternative to
            User user = new User();
            user.getUsername();*/
            UserDetails userDetails = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .username(Arrays.toString(user.getRoles().toArray(new String[0])))
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with username"+ username);
    }


}
