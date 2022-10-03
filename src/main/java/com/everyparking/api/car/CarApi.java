package com.everyparking.api.car;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.data.car.service.CarService;
import com.everyparking.api.dto.car.AddCarDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/car")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CarApi {

    private final CarService carService;

    @GetMapping()
    public ResponseEntity<?> getCarSize() {
        log.info("차 사이즈 조회");
        return ResponseEntity.ok().body(Car.CarSize.values());
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestHeader String authorization, @RequestBody AddCarDto addCarDto) {
        log.info("자동차 등록: " + addCarDto.getCarNumber());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.addCar(authorization, addCarDto));
    }


}














