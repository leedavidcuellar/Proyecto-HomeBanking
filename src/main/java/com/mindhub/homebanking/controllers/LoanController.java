package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loan> getAccounts(){
        return loanRepository.findAll();
    }

    @GetMapping("/loans/{id}")
    public LoanDTO getAccount(@PathVariable Long id){
        return loanRepository.findById(id).map(LoanDTO::new).orElse(null);
    }
}
