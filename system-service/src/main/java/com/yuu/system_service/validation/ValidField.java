package com.example.restaurant.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GeneralValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidField {
    String message() default "Dữ liệu không hợp lệ";

    int minLength() default 0;

    int maxLength() default Integer.MAX_VALUE;

    boolean requireLetters() default false;

    boolean requireNumbers() default false;

    boolean allowNumbers() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

