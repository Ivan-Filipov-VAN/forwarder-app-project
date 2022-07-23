package com.softuni.forwardingApp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CompanyUpdateDto {

    private Long id;
    private String name;
    private String vat;
    private String address;
    private String accountablePerson;



    public Long getId() {
        return id;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    @Size(min = 5, max = 10)
    @NotBlank
    public String getVat() {
        return vat;
    }

    public String getAddress() {
        return address;
    }

    public String getAccountablePerson() {
        return accountablePerson;
    }



    public CompanyUpdateDto setId(Long id) {
        this.id = id;
        return this;
    }

    public CompanyUpdateDto setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyUpdateDto setVat(String vat) {
        this.vat = vat;
        return this;
    }

    public CompanyUpdateDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public CompanyUpdateDto setAccountablePerson(String accountablePerson) {
        this.accountablePerson = accountablePerson;
        return this;
    }
}
