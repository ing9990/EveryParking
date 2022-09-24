package com.everyparking.data.user.domain;

import com.everyparking.data.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


/**
 * @author Taewoo
 */

@SpringBootTest
@RequiredArgsConstructor
class UserTest {

    private final UserRepository userRepository;

    @DisplayName("1. 도메인 테스트")
    @Test
    void 도메인_테스트() {
        var user = saveUser();
        var ruser = userRepository.findAll().get(0);

        Assertions.assertEquals(user.getEmail(), ruser.getEmail());

    }

    User saveUser() {
        return userRepository.save(User.builder().email("test@test.com").password("1234").tel("01011112222").introduce("안녕하세요.").point(100000).city(User.City.서울특별시).created(LocalDateTime.now()).updated(LocalDateTime.now()).build());
    }


}










