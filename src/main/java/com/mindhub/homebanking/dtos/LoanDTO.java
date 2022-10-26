package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {
    private Long id;

    private String name;

    private Double maxAmount;

    private List<Integer> payments = new ArrayList<>();

    private Set<ClientLoanDTO> clientLoans = new HashSet<>();

    public LoanDTO() {
    }

    public LoanDTO(Long id, String name, Double maxAmount, List<Integer> payments, Set<ClientLoanDTO> clientLoans) {
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
        this.clientLoans = loan.getClients()
                .stream()
                .map(ClientLoanDTO::new)
                .collect(Collectors.toSet());
    }
}
