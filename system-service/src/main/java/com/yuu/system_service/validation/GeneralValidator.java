package com.example.restaurant.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GeneralValidator implements ConstraintValidator<ValidField, String> {

    private int minLength;
    private int maxLength;
    private boolean requireLetters;
    private boolean requireNumbers;
    private boolean allowNumbers;

    @Override
    public void initialize(ValidField constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
        this.requireLetters = constraintAnnotation.requireLetters();
        this.requireNumbers = constraintAnnotation.requireNumbers();
        this.allowNumbers = constraintAnnotation.allowNumbers();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return buildViolation(context, "Vui lòng nhập dữ liệu");
        }

        if (!allowNumbers && value.matches(".*\\d.*")) {
            return buildViolation(context, "Dữ liệu không được chứa số");
        }

        if (value.length() < minLength || value.length() > maxLength) {
            return buildViolation(context, "Thông tin phải có từ " + minLength + " đến " + maxLength + " ký tự");
        }

        if (requireLetters && !value.matches(".*[a-zA-Z].*")) {
            return buildViolation(context, "Dữ liệu phải chứa ít nhất một chữ cái");
        }

        if (requireNumbers && !value.matches(".*\\d.*")) {
            return buildViolation(context, "Dữ liệu phải chứa ít nhất một số");
        }

        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}

