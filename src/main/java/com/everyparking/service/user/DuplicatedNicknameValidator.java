package com.everyparking.service.user;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@RequiredArgsConstructor
public class DuplicatedNicknameValidator implements ConstraintValidator<DuplicatedNicknameConstraint, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty() || !userRepository.existsUserByNickname(value));
    }
}
