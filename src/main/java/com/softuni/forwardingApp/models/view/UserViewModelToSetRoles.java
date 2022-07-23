package com.softuni.forwardingApp.models.view;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;

import java.util.ArrayList;
import java.util.List;

public class UserViewModelToSetRoles {
    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private List<UserRoleEntity> userRoles = new ArrayList<>();
    private Long idCompany;

    public Long getId() {
        return id;
    }

    public UserViewModelToSetRoles setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserViewModelToSetRoles setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserViewModelToSetRoles setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewModelToSetRoles setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModelToSetRoles setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserViewModelToSetRoles setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public UserViewModelToSetRoles setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
        return this;
    }
}
