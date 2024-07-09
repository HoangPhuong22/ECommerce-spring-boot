package vn.zerocoder.Mart.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.zerocoder.Mart.validator.impl.MinAgeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinAgeValidator.class)
public @interface MinAge {

    String message() default "Tuổi không hợp lệ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}