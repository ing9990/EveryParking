package com.everyparking.api.dto;

import com.everyparking.data.car.domain.Car;
import com.everyparking.exception.valid.DuplicatedCarNumberConstraint;
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

    @NotBlank(message = "자동차 번호가 빈칸입니다.")
    @DuplicatedCarNumberConstraint(message = "중복된 자동차 번호입니다.")
    private String carNumber;

    @NotBlank(message = "자동차 모델이 빈칸입니다.")
    private String carModel;

    private Car.CarSize size;

    public Car dtoToEntity(AddCarDto addCarDto) {
        return Car.builder()
                .carModel(addCarDto.carModel)
                .carNumber(addCarDto.carNumber)
                .carSize(addCarDto.size)
                .build();
    }
}
