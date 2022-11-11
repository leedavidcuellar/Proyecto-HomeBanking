package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;

public class LoanApplicationDTO {
    private Long loanId;
    private double amount;
    private int payments;
    private String numberAccount;
    private double maxAmount;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
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

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public LoanApplicationDTO(ClientLoan clientLoan, Account account){
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.numberAccount = account.getNumber();
        this.maxAmount = clientLoan.getLoan().getMaxAmount();
    }
}
