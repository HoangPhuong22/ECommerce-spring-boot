package vn.zerocoder.Mart.validator;

import jakarta.validation.Constraint;
import vn.zerocoder.Mart.validator.impl.PhoneValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneValid {
    String message() default "Số điện thoại không hợp lệ";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
