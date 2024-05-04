package com.mikail.weather.controller.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.val;

@Component
public class CityNameValidator implements ConstraintValidator<CityNameConstraint,String>{
    
    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //- leri boşlukla ceviriyor
        value = value.replaceAll("[^a-zA-Z0-9]","");
        return  !StringUtils.isNumeric(value) && !StringUtils.isAllBlank(value);
        
       
    }
}
