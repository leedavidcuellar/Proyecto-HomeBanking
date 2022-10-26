package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).toList();
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id){
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    @GetMapping("/transactions/byNumber/{number}")
    public List<TransactionDTO> getTransactionByNumber(@PathVariable String number){
        return transactionRepository.findByAccount_Number(number).stream().map(TransactionDTO::new).toList();
    }
}
