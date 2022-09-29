package com.everyparking.dto.car;

/**
 * @author Taewoo
 */


import java.util.*;

import com.everyparking.data.car.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCarDto {

    @NotBlank
    private String carNumber;
    
    @NotBlank
    private String carModel;

    @NotBlank
    private Car.CarSize size;

    public Car dtoToEntity(AddCarDto addCarDto) {
        return Car.builder()
                .carModel(addCarDto.carModel)
                .carNumber(addCarDto.carNumber)
                .carSize(addCarDto.size)
                .build();
    }
}
