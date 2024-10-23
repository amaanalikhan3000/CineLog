package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.UserEntity;
import com.cineLog.cineLog.repository.UserEntityRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userServiceTests {

    @Autowired
    private UserEntityRepo userEntityRepo;


    @Autowired
    private UserEntryService userEntryService;

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(UserEntity userEntity){

        assertTrue(userEntryService.saveNewUser(userEntity));
    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a,int b, int expect){
        assertEquals(expect,a+b);
    }
}
