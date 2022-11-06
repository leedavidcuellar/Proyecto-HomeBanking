package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    ////  -Buscar una lista de ClientLoan por cliente
    @GetMapping("/clientLoans/{id}")
    public List<ClientLoanDTO> getClientLoan(@PathVariable Long id){
        return clientLoanRepository.findByClient_Id(id).stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    // -Buscar una lista de ClientLoan que sean mayores a x monto pasado por parametro
    @GetMapping("/clientLoans/AmountGreaterThan/{amount}")
    public List<ClientLoanDTO> getByAmountGreaterThan(@PathVariable Double amount){
        return clientLoanRepository.findByAmountGreaterThan(amount)
                .stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    //-Buscar una lista de ClientLoan por cliente que  en cual sus balances sean menores a x monto pasado por parametro
    @GetMapping("/clientLoans/ClientAmountLessThan/{id}/{amount}")
    public List<ClientLoanDTO> getByClient_IdAndAmountLessThan(@PathVariable Long id,@PathVariable Double amount){
        return clientLoanRepository.findByClient_IdAndAmountLessThan(id,amount)
                .stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }
}
