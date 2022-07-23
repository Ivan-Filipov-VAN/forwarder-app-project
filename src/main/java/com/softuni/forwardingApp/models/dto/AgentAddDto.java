package com.softuni.forwardingApp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AgentAddDto {

    private String name;
    private String country;
    private String town;
    private String address;
    private String vat;

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    @Size(min = 2, max = 20)
    @NotBlank
    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getAddress() {
        return address;
    }

    public String getVat() {
        return vat;
    }

    public AgentAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public AgentAddDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public AgentAddDto setTown(String town) {
        this.town = town;
        return this;
    }

    public AgentAddDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public AgentAddDto setVat(String vat) {
        this.vat = vat;
        return this;
    }
}
