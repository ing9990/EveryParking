package com.everyparking.api;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.TestDto;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BasicApi {

    private final UserRepository userRepository;

    @GetMapping("/city")
    public ResponseEntity getCities(@RequestParam(required = false) Integer listSize) {
        log.info("City 요청");
        if (listSize == null) return ResponseEntity.ok().body(User.City.values());
        return ResponseEntity.ok().body(Arrays.stream(User.City.values()).limit(listSize));
    }

    @GetMapping("/{id}")
    public @ResponseBody
    TestDto getUsers(@PathVariable Long id) {
        return TestDto.builder()
                      .id(id + "")
                      .username("유저" + id)
                      .introduce("안녕하세요!! 저는 " + id + "번 입니다.")
                      .build();
    }
}









