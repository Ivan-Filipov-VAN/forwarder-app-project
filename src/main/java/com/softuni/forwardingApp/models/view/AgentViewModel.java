package com.softuni.forwardingApp.models.view;

public class AgentViewModel {

    private Long id;
    private String name;
    private String country;
    private String town;
    private String address;
    private String vat;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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

    public AgentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public AgentViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public AgentViewModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public AgentViewModel setTown(String town) {
        this.town = town;
        return this;
    }

    public AgentViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public AgentViewModel setVat(String vat) {
        this.vat = vat;
        return this;
    }
}
