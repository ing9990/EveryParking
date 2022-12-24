package com.everyparking.api.car;


import com.everyparking.api.dto.AddCarDto;
import com.everyparking.data.car.domain.Car;
import com.everyparking.data.car.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/car")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CarApi {

    private final CarService carService;

    @GetMapping()
    public ResponseEntity<?> getCarSize() {
        return ResponseEntity.ok().body(Car.CarSize.values());
    }

    @GetMapping("/dat")
    public ResponseEntity<?> getCars() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization, @Valid @RequestBody AddCarDto addCarDto) {
        log.info("자동차 등록: " + addCarDto.getCarNumber());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.addCar(authorization, addCarDto));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyCar(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.getMyCar(authorization));
    }


}














