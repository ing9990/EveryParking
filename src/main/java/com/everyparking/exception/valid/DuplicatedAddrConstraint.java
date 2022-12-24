package com.everyparking.exception.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = DuplicatedAddrValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedAddrConstraint {
    String message() default "주소가 중복되었습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}