package com.everyparking.exception.valid;

/**
 * @author Taewoo
 */


import com.everyparking.data.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class DuplicatedAddrValidator implements ConstraintValidator<DuplicatedAddrConstraint, String> {
    private final PlaceRepository placeRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty() || !placeRepository.existsByAddr(value)
                || !placeRepository.existsByName(value));
    }
}
