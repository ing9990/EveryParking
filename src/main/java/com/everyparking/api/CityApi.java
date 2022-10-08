package com.everyparking.api;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin("*")
public class CityApi {

    @GetMapping
    public ResponseEntity getCities(@RequestParam(required = false) Integer listSize) {
        log.info("City 요청");
        if (listSize == null) return ResponseEntity.ok().body(User.City.values());
        return ResponseEntity.ok().body(Arrays.stream(User.City.values()).limit(listSize));
    }

}









