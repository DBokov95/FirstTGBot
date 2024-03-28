package com.bokov.FirstTGBot.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "BTC")
public class BTC {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "prise")
    private double prise;

    @Column( name = "date_of_request")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfRequest;

    public BTC() {
    }

    public BTC(double prise, Date dateOfRequest) {
        this.prise = prise;
        this.dateOfRequest = dateOfRequest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrise() {
        return prise;
    }

    public void setPrise(double prise) {
        this.prise = prise;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

}
