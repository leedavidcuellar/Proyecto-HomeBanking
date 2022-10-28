package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Random;

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
    private String cardHolder;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(String number, CardColor color, CardType type, LocalDate fromDate, Client client) {
        this.client = client;
        this.number = number;
        this.color = color;
        this.type = type;
        this.fromDate = fromDate;
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        switch (this.color) {
            case GOLD :  this.thruDate = LocalDate.now().plusYears(5); break;
            case SILVER: this.thruDate = LocalDate.now().plusYears(3); break;
            case TITANIUM: this.thruDate = LocalDate.now().plusYears(4); break;
            default: this.thruDate = this.fromDate; break;

        }

        this.cvv = generateCVV();
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

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private String generateCVV(){
        Random aleatorio = new Random();
        return String.valueOf(aleatorio.nextInt(100,999));
    }
}

/*
 private String generateCVC(){
        int newCVC = (int) (Math.random() * (999 - 100)+100);
        if(newCVC<10){
            return "00"+ String.valueOf(newCVC);
        }
        if(newCVC<100){
            return "0"+ String.valueOf(newCVC);
        }
        return String.valueOf(newCVC);
    }
 */