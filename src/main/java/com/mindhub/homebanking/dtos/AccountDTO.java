package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;

    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Long id, String number, LocalDateTime creationDate, Double balance) {
        this.id = id;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
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

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
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
}
