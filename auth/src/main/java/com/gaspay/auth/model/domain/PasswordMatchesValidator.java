package com.gaspay.auth.model.domain;


import com.gaspay.auth.interfaces.PasswordMatches;
import com.gaspay.auth.model.dto.SignupDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignupDto userDto = (SignupDto) o;
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}
