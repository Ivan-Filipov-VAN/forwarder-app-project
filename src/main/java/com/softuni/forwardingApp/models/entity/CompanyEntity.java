package com.softuni.forwardingApp.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseEntity {

    private String name;
    private String vat;
    private String address;
    private String accountablePerson;

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Column(nullable = false, unique = true)
    public String getVat() {
        return vat;
    }

    @Column
    public String getAddress() {
        return address;
    }

    @Column
    public String getAccountablePerson() {
        return accountablePerson;
    }

    public CompanyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyEntity setVat(String vat) {
        this.vat = vat;
        return this;
    }

    public CompanyEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public CompanyEntity setAccountablePerson(String accountablePerson) {
        this.accountablePerson = accountablePerson;
        return this;
    }
}
