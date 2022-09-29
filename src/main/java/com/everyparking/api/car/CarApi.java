package com.everyparking.api.car;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.data.car.service.CarService;
import com.everyparking.dto.car.AddCarDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api/car")
@CrossOrigin("*")
@RequiredArgsConstructor
@JsonRootName("car")
public class CarApi {

    private final CarService carService;

    @GetMapping()
    public ResponseEntity<?> getCarSize() {
        return ResponseEntity.ok().body(Car.CarSize.values());
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestHeader String jwt, @RequestBody AddCarDto addCarDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.addCar(jwt, addCarDto));
    }


}














