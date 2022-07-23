package com.softuni.forwardingApp.models.dto;

import com.softuni.forwardingApp.validation.FieldMatch;
import com.softuni.forwardingApp.validation.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords do not match."
)
public class UserRegisterDto {

    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String compName;
    private String compVAT;

    @Email
    @NotBlank
    @UniqueUserEmail
    public String getEmail() {
        return email;
    }

    @Size(min = 2, max = 20)
    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    @Size(min = 2, max = 20)
    @NotBlank
    public String getLastName() {
        return lastName;
    }

    @Size(min = 2, max = 20)
    @NotBlank
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getPassword() {
        return password;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getCompName() {
        return compName;
    }

    @Size(min = 6, max = 10)
    @NotBlank
    public String getCompVAT() {
        return compVAT;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRegisterDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRegisterDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRegisterDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public UserRegisterDto setCompName(String compName) {
        this.compName = compName;
        return this;
    }

    public UserRegisterDto setCompVAT(String compVAT) {
        this.compVAT = compVAT;
        return this;
    }
}
