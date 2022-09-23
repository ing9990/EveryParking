package com.everyparking.api.parking;

/**
 * @author Taewoo
 */


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/parking")
public class ParkingApi {

    @GetMapping({"/", ""})
    public ResponseEntity test() {
        return ResponseEntity.ok().body("TEST");
    }
}
