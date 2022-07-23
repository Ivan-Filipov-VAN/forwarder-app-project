package com.softuni.forwardingApp.models.dto;

import com.softuni.forwardingApp.validation.UniqueCompanyName;
import com.softuni.forwardingApp.validation.UniqueCompanyVat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CompanyAddDto {

    private String name;
    private String vat;
    private String address;
    private String accountablePerson;

    @Size(min = 3, max = 20)
    @NotBlank
    @UniqueCompanyName
    public String getName() {
        return name;
    }

    @Size(min = 5, max = 10)
    @NotBlank
    @UniqueCompanyVat
    public String getVat() {
        return vat;
    }

    public String getAddress() {
        return address;
    }

    public String getAccountablePerson() {
        return accountablePerson;
    }


    public CompanyAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyAddDto setVat(String vat) {
        this.vat = vat;
        return this;
    }

    public CompanyAddDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public CompanyAddDto setAccountablePerson(String accountablePerson) {
        this.accountablePerson = accountablePerson;
        return this;
    }
}
