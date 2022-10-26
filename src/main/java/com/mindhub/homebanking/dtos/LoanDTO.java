package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanDTO {
    private Long id;

    private String name;

    private Double maxAmount;

    private List<Integer> payments = new ArrayList<>();

    private Set<ClientLoan> clientLoans = new HashSet<>();

    public LoanDTO() {
    }

    public LoanDTO(Long id, String name, Double maxAmount, List<Integer> payments, Set<ClientLoan> clientLoans) {
        this.id = id;
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.clientLoans = clientLoans;
    }

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.clientLoans = loan.getClientLoans();
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

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
}
