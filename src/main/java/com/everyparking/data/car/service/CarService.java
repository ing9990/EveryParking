package com.everyparking.data.car.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.AddCarDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.car.repository.CarRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final JwtTokenUtils jwtTokenUtils;
    private final CarRepository carRepository;

    public Object addCar(String jwt, AddCarDto addCarDto) {

        var user = jwtTokenUtils.getUserByToken(jwt);
        var car = addCarDto.dtoToEntity(addCarDto);

        car.setUser(user);
        carRepository.save(car);

        return DefaultResponseDtoEntity.ok("자동차 등록 성공", car);
    }

    public DefaultResponseDtoEntity getAll() {
        return DefaultResponseDtoEntity
                .ok("자동차 조회 성공", carRepository.findAll());
    }
}
