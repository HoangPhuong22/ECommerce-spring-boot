package vn.zerocoder.Mart.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.zerocoder.Mart.validator.MinAge;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    private int minAge;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }
        long years = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());

        return years >= minAge;
    }
}