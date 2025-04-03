package com.yuu.admin_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GeneralValidator.class) // Trỏ tới validator đã tạo
@Target({ ElementType.FIELD, ElementType.PARAMETER }) // Áp dụng cho trường và tham số
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidField {
    String message() default "Dữ liệu không hợp lệ"; // Thông báo lỗi mặc định

    int minLength() default 0; // Chiều dài tối thiểu

    int maxLength() default Integer.MAX_VALUE; // Chiều dài tối đa

    boolean requireLetters() default false; // Yêu cầu chứa chữ cái

    boolean requireNumbers() default false; // Yêu cầu chứa số

    boolean allowNumbers() default true; // Cho phép chứa số (tương tự như yêu cầu của bạn)

    Class<?>[] groups() default {}; // Các nhóm phân loại

    Class<? extends Payload>[] payload() default {}; // Thông tin bổ sung
}
