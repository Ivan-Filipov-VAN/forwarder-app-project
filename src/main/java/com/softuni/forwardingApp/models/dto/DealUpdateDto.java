package com.softuni.forwardingApp.models.dto;

import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.enums.AirTypeEnum;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DealUpdateDto {

    private Long id;
    private AirTypeEnum type;
    private LocalDate date;
    private String mawb;
    private String hawb;
    private CompanyEntity company;
    private AgentEntity agent;
    private UserEntity employee;
    private Integer pieces;
    private Double actualWeight;
    private Double chargeableWeight;
    private String country;
    private String airport;

    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public AirTypeEnum getType() {
        return type;
    }

    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    @Column(nullable = false)
    public String getMawb() {
        return mawb;
    }

    @Column
    public String getHawb() {
        return hawb;
    }

    @ManyToOne(optional = false)
    @NotNull
    public CompanyEntity getCompany() {
        return company;
    }

    @ManyToOne
    public AgentEntity getAgent() {
        return agent;
    }

    @ManyToOne
    public UserEntity getEmployee() {
        return employee;
    }

    @Column
    public Integer getPieces() {
        return pieces;
    }

    @Column
    public Double getActualWeight() {
        return actualWeight;
    }

    @Column
    public Double getChargeableWeight() {
        return chargeableWeight;
    }

    @Column
    public String getCountry() {
        return country;
    }

    @Column
    public String getAirport() {
        return airport;
    }

    public DealUpdateDto setId(Long id) {
        this.id = id;
        return this;
    }

    public DealUpdateDto setType(AirTypeEnum type) {
        this.type = type;
        return this;
    }

    public DealUpdateDto setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public DealUpdateDto setMawb(String mawb) {
        this.mawb = mawb;
        return this;
    }

    public DealUpdateDto setHawb(String hawb) {
        this.hawb = hawb;
        return this;
    }

    public DealUpdateDto setCompany(CompanyEntity company) {
        this.company = company;
        return this;
    }

    public DealUpdateDto setAgent(AgentEntity agent) {
        this.agent = agent;
        return this;
    }

    public DealUpdateDto setEmployee(UserEntity employee) {
        this.employee = employee;
        return this;
    }

    public DealUpdateDto setPieces(Integer pieces) {
        this.pieces = pieces;
        return this;
    }

    public DealUpdateDto setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
        return this;
    }

    public DealUpdateDto setChargeableWeight(Double chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
        return this;
    }

    public DealUpdateDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public DealUpdateDto setAirport(String airport) {
        this.airport = airport;
        return this;
    }
}
