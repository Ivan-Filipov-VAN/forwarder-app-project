package com.softuni.forwardingApp.models.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private List<UserRoleEntity> userRoles = new ArrayList<>();
    private CompanyEntity company;
    private String compName;
    private String compVAT;

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getUserRoles() {
        return userRoles ;
    }

    @ManyToOne
    public CompanyEntity getCompany() {
        return company;
    }

    @Column(nullable = false)
    public String getCompName() {
        return compName;
    }

    @Column(nullable = false)
    public String getCompVAT() {
        return compVAT;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserEntity setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public UserEntity setCompany(CompanyEntity company) {
        this.company = company;
        return this;
    }

    public void addRole(UserRoleEntity userRole) {
        this.userRoles.add(userRole);
    }

    public UserEntity setCompName(String compName) {
        this.compName = compName;
        return this;
    }

    public UserEntity setCompVAT(String compVAT) {
        this.compVAT = compVAT;
        return this;
    }
}
