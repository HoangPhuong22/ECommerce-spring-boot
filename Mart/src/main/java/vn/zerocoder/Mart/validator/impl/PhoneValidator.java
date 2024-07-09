package vn.zerocoder.Mart.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.zerocoder.Mart.validator.PhoneValid;

public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {
    @Override
    public void initialize(PhoneValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.matches("^(0|\\+84)\\d{9,10}$");
    }
}
