package com.everyparking.data.place.service.valid;

/**
 * @author Taewoo
 */


import com.everyparking.data.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@RequiredArgsConstructor
public class DuplicatedPlaceNameValidator implements ConstraintValidator<DuplicatedPlaceNameConstraint, String> {
    private final PlaceRepository placeRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty() || !placeRepository.existsByName(value));
    }
}