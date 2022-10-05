package com.everyparking.api.parking;

/**
 * @author Taewoo
 */


import com.everyparking.data.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> getMyPlace(@RequestHeader(value = "authorization") String authorization) {
        log.info("장소 조회: " + authorization);

        return ResponseEntity.ok()
                .body(rentService.getMyPlace(authorization));
    }


}
