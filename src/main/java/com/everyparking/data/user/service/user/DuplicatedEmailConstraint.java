package com.everyparking.data.user.service.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Taewoo
 */

@Constraint(validatedBy = DuplicatedEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedEmailConstraint {
    String message() default "이메일이 중복되었습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}