package com.softuni.forwardingApp.models.view;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<UserRoleEntity> userRoles = new ArrayList<>();
    private CompanyEntity company;

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserViewModel setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public UserViewModel setCompany(CompanyEntity company) {
        this.company = company;
        return this;
    }
}
