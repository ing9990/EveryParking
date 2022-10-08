package com.everyparking.api.parking;

/**
 * @author Taewoo
 */


import com.everyparking.data.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/parking")
public class ParkingApi {

    private final RentService rentService;

    @GetMapping
    public ResponseEntity<?> getMyPlace(@RequestHeader(name = HttpHeaders.AUTHORIZATION)String authorization) {
        log.info("User 접속");

        return ResponseEntity.ok()
                .body(rentService.getMyPlace(authorization));
    }


}
