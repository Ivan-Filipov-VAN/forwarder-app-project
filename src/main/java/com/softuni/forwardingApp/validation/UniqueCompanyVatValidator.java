package com.softuni.forwardingApp.validation;

import com.softuni.forwardingApp.repositories.CompanyRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCompanyVatValidator implements ConstraintValidator<UniqueCompanyVat, String> {

    private final CompanyRepository companyRepository;

    public UniqueCompanyVatValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return companyRepository.
                findByVat(value).
                isEmpty();
    }
}
