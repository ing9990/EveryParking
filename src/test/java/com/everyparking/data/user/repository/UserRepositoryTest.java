package com.everyparking.data.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Taewoo
 */

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("1.[Not Null] UserRepository")
    @Test
    void NN_유저_전체_테스트() {
        assertNotNull(userRepository);
    }

    @DisplayName("2.[Not Null] User")
    @Test
    void NN_유저_테스트() {
        var user = userRepository.findAll().get(0);
        assertNotNull(user);
    }

    @DisplayName("3.[Not Null] userInfo")
    @Test
    void NN_유저_정보_테스트() {
        var email = userRepository.findAll().get(0).getEmail();
        assertNotNull(email);
    }
}