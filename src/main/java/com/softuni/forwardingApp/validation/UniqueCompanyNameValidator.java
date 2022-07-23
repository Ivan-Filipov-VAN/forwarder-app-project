package com.softuni.forwardingApp.validation;

import com.softuni.forwardingApp.repositories.CompanyRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String>  {

    private final CompanyRepository companyRepository;

    public UniqueCompanyNameValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return companyRepository.
                findByName(value).
                isEmpty();
    }
}
