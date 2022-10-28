package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;

    private String cardHeader;

    private Long cardGoodThru;

    @OneToMany
    private List<Card> cards;

    public Bank() {
    }

    public Bank(String name, String cardHeader, Long cardGoodThru) {
        this.name = name;
        this.cardHeader = cardHeader;
        this.cardGoodThru = cardGoodThru;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardHeader() {
        return cardHeader;
    }

    public void setCardHeader(String cardHeader) {
        this.cardHeader = cardHeader;
    }

    public Long getCardGoodThru() {
        return cardGoodThru;
    }

    public void setCardGoodThru(Long cardGoodThru) {
        this.cardGoodThru = cardGoodThru;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
