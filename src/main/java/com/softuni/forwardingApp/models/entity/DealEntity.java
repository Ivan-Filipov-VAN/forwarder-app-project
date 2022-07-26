package com.softuni.forwardingApp.models.entity;

import com.softuni.forwardingApp.models.enums.AirTypeEnum;
import com.softuni.forwardingApp.models.enums.ShipmentStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deals")
public class DealEntity extends BaseEntity {

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

    private ShipmentStatusEnum status;

    private BigDecimal sell;
    private BigDecimal net;
    private Boolean archive;

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

    @ManyToOne
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

    @Enumerated(EnumType.STRING)
    public ShipmentStatusEnum getStatus() {
        return status;
    }

    public DealEntity setType(AirTypeEnum type) {
        this.type = type;
        return this;
    }

    public DealEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public DealEntity setMawb(String mawb) {
        this.mawb = mawb;
        return this;
    }

    public DealEntity setHawb(String hawb) {
        this.hawb = hawb;
        return this;
    }

    public DealEntity setCompany(CompanyEntity company) {
        this.company = company;
        return this;
    }

    public DealEntity setAgent(AgentEntity agent) {
        this.agent = agent;
        return this;
    }

    public DealEntity setEmployee(UserEntity employee) {
        this.employee = employee;
        return this;
    }

    public DealEntity setPieces(Integer pieces) {
        this.pieces = pieces;
        return this;
    }

    public DealEntity setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
        return this;
    }

    public DealEntity setChargeableWeight(Double chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
        return this;
    }

    public DealEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public DealEntity setAirport(String airport) {
        this.airport = airport;
        return this;
    }

    public DealEntity setStatus(ShipmentStatusEnum status) {
        this.status = status;
        return this;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public DealEntity setSell(BigDecimal sell) {
        this.sell = sell;
        return this;
    }

    public BigDecimal getNet() {
        return net;
    }

    public DealEntity setNet(BigDecimal net) {
        this.net = net;
        return this;
    }

    public Boolean getArchive() {
        return archive;
    }

    public DealEntity setArchive(Boolean archive) {
        this.archive = archive;
        return this;
    }
}
