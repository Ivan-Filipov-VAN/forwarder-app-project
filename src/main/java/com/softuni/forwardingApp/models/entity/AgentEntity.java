package com.softuni.forwardingApp.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "agents")
public class AgentEntity extends BaseEntity {

    private String name;
    private String country;
    private String town;
    private String address;
    private String vat;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public String getCountry() {
        return country;
    }

    @Column
    public String getTown() {
        return town;
    }

    @Column
    public String getAddress() {
        return address;
    }

    @Column
    public String getVat() {
        return vat;
    }

    public AgentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public AgentEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public AgentEntity setTown(String town) {
        this.town = town;
        return this;
    }

    public AgentEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public AgentEntity setVat(String vat) {
        this.vat = vat;
        return this;
    }
}
