package com.everyparking.data.car.service.valid;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.repository.CarRepository;
import com.everyparking.data.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class DuplicatedCarNumberValidator implements ConstraintValidator<DuplicatedCarNumberConstraint, String> {
    private final CarRepository carRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty() || !carRepository.existsByCarNumber(value));
    }
}
