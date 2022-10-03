package com.everyparking.data.car.service;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.repository.CarRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import com.everyparking.dto.car.AddCarDto;
import lombok.RequiredArgsConstructor;
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

        return carRepository.save(car);
    }
}
