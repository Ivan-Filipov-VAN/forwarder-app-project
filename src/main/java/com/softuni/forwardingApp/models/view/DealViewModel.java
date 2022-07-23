package com.softuni.forwardingApp.models.view;

import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.enums.AirTypeEnum;

import java.time.LocalDate;

public class DealViewModel {

    private Long id;
    private AirTypeEnum type;
    private LocalDate date;
    private String mawb;
    private String hawb;
    private String company;
    private String agent;
    private String employee;
    private Integer pieces;
    private Double actualWeight;
    private Double chargeableWeight;
    private String country;
    private String airport;

    public DealViewModel(Long id,
                         AirTypeEnum type,
                         LocalDate date,
                         String mawb,
                         String hawb,
                         String company,
                         String agent,
                         String employee,
                         Integer pieces,
                         Double actualWeight,
                         Double chargeableWeight,
                         String country,
                         String airport) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.mawb = mawb;
        this.hawb = hawb;
        this.company = company;
        this.agent = agent;
        this.employee = employee;
        this.pieces = pieces;
        this.actualWeight = actualWeight;
        this.chargeableWeight = chargeableWeight;
        this.country = country;
        this.airport = airport;
    }

    public Long getId() {
        return id;
    }

    public AirTypeEnum getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMawb() {
        return mawb;
    }

    public String getHawb() {
        return hawb;
    }

    public String getCompany() {
        return company;
    }

    public String getAgent() {
        return agent;
    }

    public String getEmployee() {
        return employee;
    }

    public Integer getPieces() {
        return pieces;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public Double getChargeableWeight() {
        return chargeableWeight;
    }

    public String getCountry() {
        return country;
    }

    public String getAirport() {
        return airport;
    }
}
