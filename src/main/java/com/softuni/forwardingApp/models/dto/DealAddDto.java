package com.softuni.forwardingApp.models.dto;

import com.softuni.forwardingApp.models.enums.AirTypeEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DealAddDto {
    private AirTypeEnum type;
    private LocalDate date;
    private String mawb;
    private String hawb;
//    private CompanyEntity customer;
    private Long idCompany;
//    private AgentEntity agent;
    private Long idAgent;
    private Long idEmployee;
    private Integer pieces;
    private Double actualWeight;
    private Double chargeableWeight;
    private String country;
    private String airport;

    @NotNull
    public AirTypeEnum getType() {
        return type;
    }

    public DealAddDto setType(AirTypeEnum type) {
        this.type = type;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public DealAddDto setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getMawb() {
        return mawb;
    }

    public DealAddDto setMawb(String mawb) {
        this.mawb = mawb;
        return this;
    }

    public String getHawb() {
        return hawb;
    }

    public DealAddDto setHawb(String hawb) {
        this.hawb = hawb;
        return this;
    }

    @NotNull
    public Long getIdCompany() {
        return idCompany;
    }

    public DealAddDto setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
        return this;
    }

    public Long getIdAgent() {
        return idAgent;
    }

    public DealAddDto setIdAgent(Long idAgent) {
        this.idAgent = idAgent;
        return this;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public DealAddDto setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
        return this;
    }

    public Integer getPieces() {
        return pieces;
    }

    public DealAddDto setPieces(Integer pieces) {
        this.pieces = pieces;
        return this;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public DealAddDto setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
        return this;
    }

    public Double getChargeableWeight() {
        return chargeableWeight;
    }

    public DealAddDto setChargeableWeight(Double chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public DealAddDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getAirport() {
        return airport;
    }

    public DealAddDto setAirport(String airport) {
        this.airport = airport;
        return this;
    }
}
