package com.everyparking.service.user;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@RequiredArgsConstructor
public class DuplicatedEmailValidator implements ConstraintValidator<DuplicatedEmailConstraint, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty() || !userRepository.existsUserByEmail(value));
    }
}
