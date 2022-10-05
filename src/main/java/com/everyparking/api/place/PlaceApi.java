package com.everyparking.api.place;

/**
 * @author Taewoo
 */


import com.everyparking.data.place.service.PlaceService;
import com.everyparking.api.dto.place.PlaceRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/place")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlaceApi {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<?> getAllPlace() {
        return ResponseEntity.ok().body(placeService.getAllPlace());
    }


    @PostMapping
    public ResponseEntity<?> addPlace(@RequestHeader(name = "Authorization") String authorization, @Valid @RequestBody PlaceRequestDto placeRequestDto) {
        log.info(placeRequestDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(placeService.addPlace(authorization, placeRequestDto));
    }


}
