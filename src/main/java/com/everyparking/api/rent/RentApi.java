package com.everyparking.api.rent;

import com.everyparking.api.dto.AddRentDto;
import com.everyparking.data.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rent")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class RentApi {

    private final RentService rentService;

    @GetMapping("")
    public ResponseEntity<?> getAllRent() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<?> cancelRent(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long placeId
    ) {
        return ResponseEntity.ok(rentService.cancelRent(authorization, placeId));
    }

    @PostMapping("")
    public ResponseEntity<?> addRent(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody AddRentDto addRentDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(rentService.addRent(authorization, addRentDto));
    }
}