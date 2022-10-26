package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private Long id;
    private double amount;
    private int payments;
    private Loan loans;
    private Client client;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(Long id, double amount, int payments, Loan loans, Client client) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.loans = loans;
        this.client = client;
    }

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loans = clientLoan.getLoan();
        this.client = clientLoan.getClient();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Loan getLoans() {
        return loans;
    }

    public void setLoans(Loan loans) {
        this.loans = loans;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
