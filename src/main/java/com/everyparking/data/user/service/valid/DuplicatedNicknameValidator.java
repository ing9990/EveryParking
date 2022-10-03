package com.everyparking.data.user.service.valid;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class DuplicatedNicknameValidator implements ConstraintValidator<DuplicatedNicknameConstraint, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty() || !userRepository.existsUserByNickname(value));
    }
}
