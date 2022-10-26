package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private Account account;
    private TransactionType type;

    public TransactionDTO(Long id, Double amount, String description, LocalDateTime date, Account account, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.type = type;
    }

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.account = transaction.getAccount();
        this.type = transaction.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {

        String finalDescription;
        finalDescription = this.description;

        switch (this.type){
            case CREDIT : finalDescription= finalDescription + " - CREDIT";
                break;
            case DEBIT:  finalDescription= finalDescription + " - DEBIT";
                break;
            default:  finalDescription= finalDescription + " - ERROR";
        }

        if (this.date.toLocalDate().isEqual(LocalDate.now())) {
            finalDescription = finalDescription + " - Now";
        }else if (this.date.toLocalDate().isBefore(LocalDate.now())) {
            finalDescription = finalDescription + " - Before";
        } else if (this.date.toLocalDate().isAfter(LocalDate.now())) {
            finalDescription = finalDescription + " - Marty McFly?";
        }

        return finalDescription;
    }
}
