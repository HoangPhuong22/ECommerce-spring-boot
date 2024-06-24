package vn.zerocoder.Mart.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.zerocoder.Mart.validator.impl.FileNotEmptyValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileNotEmptyValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNotEmpty {
    String message() default "File không được để trống";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}