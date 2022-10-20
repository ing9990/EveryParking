package com.everyparking.data.car.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.AddCarDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.car.domain.Car;
import com.everyparking.data.car.repository.CarRepository;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.service.JwtTokenUtils;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final JwtTokenUtils jwtTokenUtils;
    private final CarRepository carRepository;

    @Transactional
    public DefaultResponseDtoEntity addCar(String jwt, AddCarDto addCarDto) {

        if (addCarDto.getSize() == null)
            addCarDto.setSize(Car.CarSize.소형);

        var user = jwtTokenUtils.getUserByToken(jwt);
        var car = addCarDto.dtoToEntity(addCarDto);

        updateUser(car, user);
        return DefaultResponseDtoEntity.ok("자동차 등록 성공", car);
    }

    @Transactional
    public void updateUser(Car car, User user) {
        car.setUser(user);
        carRepository.save(car);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAll() {
        return DefaultResponseDtoEntity
                .ok("자동차 조회 성공", carRepository.findAll());
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getMyCar(String authorization) {
        var user = jwtTokenUtils.getUserByToken(authorization);

        return DefaultResponseDtoEntity
                .ok("내 차 조회 성공", carRepository.findCarsByUser(user));
    }

    @Transactional(readOnly = true)
    public Car getCarByCarNumber(String carNumber) {
        return carRepository.findCarByCarNumber(carNumber);
    }

    public List<Car> findCarsByUserId(User user) {
        return carRepository.findCarsByUser(user);
    }
}
