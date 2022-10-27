package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.mindhub.homebanking.models.Client;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String number;

    private CardColor color;

    private CardType type;

    private LocalDate fromDate;
    private LocalDate thruDate;
    private String cvv;
    private String cardholder;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(String number, CardColor color, CardType type, LocalDate fromDate, Client client) {
        this.number = number;
        this.color = color;
        this.type = type;
        this.fromDate = fromDate;
        this.client = client;
        this.cardholder = client.getFirstName() + " " + client.getLastName();
        switch (this.color) {
            case GOLD :  this.thruDate = LocalDate.now().plusYears(5); break;
            case SILVER: this.thruDate = LocalDate.now().plusYears(3); break;
            case TITANIUM: this.thruDate = LocalDate.now().plusYears(4); break;
            default: this.thruDate = this.fromDate; break;

        }

        this.cvv = generateCVC();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvc) {
        this.cvv = cvc;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private String generateCVC(){
        int newCVC = (int) (Math.random() * (999 - 100)*100);
        if(newCVC<10){
            return "00"+ String.valueOf(newCVC);
        }
        if(newCVC<100){
            return "0"+ String.valueOf(newCVC);
        }
        return String.valueOf(newCVC);
    }
}
