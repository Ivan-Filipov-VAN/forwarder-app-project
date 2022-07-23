package com.softuni.forwardingApp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AgentUpdateDto {

    private Long id;
    private String name;
    private String country;
    private String town;
    private String address;
    private String vat;

    public Long getId() {
        return id;
    }

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

    public AgentUpdateDto setId(Long id) {
        this.id = id;
        return this;
    }

    public AgentUpdateDto setName(String name) {
        this.name = name;
        return this;
    }

    public AgentUpdateDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public AgentUpdateDto setTown(String town) {
        this.town = town;
        return this;
    }

    public AgentUpdateDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public AgentUpdateDto setVat(String vat) {
        this.vat = vat;
        return this;
    }
}
