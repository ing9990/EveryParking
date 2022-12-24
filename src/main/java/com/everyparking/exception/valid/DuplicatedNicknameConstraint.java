package com.everyparking.exception.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = DuplicatedNicknameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedNicknameConstraint {
    String message() default "닉네임이 중복되었습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
