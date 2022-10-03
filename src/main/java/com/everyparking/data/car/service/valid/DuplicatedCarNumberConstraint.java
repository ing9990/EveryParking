package com.everyparking.data.car.service.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Taewoo
 */

@Constraint(validatedBy = DuplicatedCarNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedCarNumberConstraint {
    String message() default "이미 등록된 차량입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}