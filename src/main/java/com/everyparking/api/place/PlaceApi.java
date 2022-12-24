package com.everyparking.api.place;


import com.everyparking.api.dto.PlaceRequestDto;
import com.everyparking.data.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/place")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlaceApi {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<?> getAllPlace() {
        return ResponseEntity.ok().body(placeService.getAllPlace());
    }

    @PostMapping
    public ResponseEntity<?> addPlace(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody PlaceRequestDto placeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(placeService.addPlace(authorization, placeRequestDto));
    }


}
