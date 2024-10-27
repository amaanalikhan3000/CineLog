package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.UserEntityRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//@SpringBootTest
public class UserDetailsServiceImplTest {

   //@Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;


   @Mock
   private UserEntityRepo userEntityRepo;

   @BeforeAll
   void setUp(){
       MockitoAnnotations.initMocks(this);
   }

   @Test
    void loadUserByUsernameTest(){
        when(userEntityRepo.findByusername(ArgumentMatchers.anyString())).thenReturn((UserEntity) User.builder().username("ram").password("ram").roles(String.valueOf(new ArrayList<>())).build());
       UserDetails user = userDetailsService.loadUserByUsername("Ram");
       Assertions.assertNotNull(user);
    }
}
