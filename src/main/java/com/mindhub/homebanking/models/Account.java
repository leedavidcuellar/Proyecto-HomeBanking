package com.mindhub.homebanking.models;
import javax.persistence.Entity;

import com.mindhub.homebanking.repositories.AccountRepository;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String number;

    private LocalDateTime creationDate;

    private Double balance;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {
    }

    public Account(String number, LocalDateTime creationDate, Double balance, Client cliente) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = cliente;
    }

    public Account(Client cliente) {
        this.number = generateNumber();
        this.creationDate = LocalDateTime.now();
        this.balance = 0.00;
        this.client = cliente;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client cliente) {
        this.client = cliente;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", cliente=" + client +
                '}';
    }


    public void updateBalance(TransactionType type, double amount) {
        double total = getBalance();

        switch (type) {
            case CREDIT:
                total = total + amount;
                break;
            case DEBIT:
                total = total + amount;
                break;
        }
        setBalance(total);
    }

    private String generateNumber(){
        Random aleatorio = new Random();
        String aux = "";
        int num = aleatorio.nextInt(0, 999);
        if (num < 10) {
            return aux = "VIN00" + num;
        }
        if (num < 100) {
            return aux = "VIN0" + num;
        }
        return aux = "VIN" + num;

    }

    /*
    public double getBalance(){
     double total =0.0;
        for(Transaction transaction:gertTransacion()){
            switch(transaction.getType()){
             case DEBIT: total = total + amount;
                    break;
             case CREDIT: total = total + amount;
                    break;
            }
        }
    }
    */
}
