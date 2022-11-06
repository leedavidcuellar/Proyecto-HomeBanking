package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    //-Buscar una lista de transacciones entre dos fechas pasadas por parametro

    @GetMapping("/transactions/BetweenDates/")
    public List<TransactionDTO> getByCreationDateLessThan(@RequestParam String date1, @RequestParam String date2){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return transactionRepository.findByDateBetween(LocalDateTime.parse(date1, formatter),LocalDateTime.parse(date2, formatter))
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toList());
    }

    //-Buscar una lista de transacciones por type
    @GetMapping("/transactions/TransactionType/{type}")
    public List<TransactionDTO> getByTransactionType(@PathVariable TransactionType type){
        return transactionRepository.findByType(type)
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toList());
    }

    // -Buscar una lista de transacciones  monto sea mayor a x monto pasado como primer parametro,y menor a x monto pasado por parametro
    @GetMapping("/transactions/AmountGreaterLessThan/{amount1}/{amount2}")
    public List<TransactionDTO> getByAmountGreaterThanAndAmountLessThan(@PathVariable Double amount1, @PathVariable Double amount2){
        return transactionRepository.findByAmountGreaterThanAndAmountLessThan(amount1,amount2)
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toList());
    }

}
