package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public List<Loan> getLoans(){
        return loanRepository.findAll();
    }

    @GetMapping("/loans/{id}")
    public LoanDTO getLoan(@PathVariable Long id){
        return loanRepository.findById(id).map(LoanDTO::new).orElse(null);
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Client client  = clientRepository.findByEmail(authentication.getName()).get();
        Optional<Loan> loan=loanRepository.findById(loanApplicationDTO.getLoanId());
        if(!loan.isPresent()){
            return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
        }
        Optional<Account> account= accountRepository.findByNumber(loanApplicationDTO.getNumberAccount());
        if(!account.isPresent()){
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getLoanId()<=0||loanApplicationDTO.getNumberAccount().isEmpty()|| loanApplicationDTO.getPayments()==0) {
            return new ResponseEntity<>("This payments doesn't exist for this loan", HttpStatus.FORBIDDEN);
        }
        if (!loan.get().getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("This number of payments is not enabled fot this loan", HttpStatus.FORBIDDEN);
        }
        if (!loan.get().getMaxAmount().equals(loanApplicationDTO.getMaxAmount())){
            return new ResponseEntity<>("This amount is bigger to this loan", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(loanApplicationDTO.getAmount(), loan.get().getName()+" Loan Approved", LocalDateTime.now(), account.get(), TransactionType.CREDIT);
        transactionRepository.save(transaction);
        account.get().setBalance(account.get().getBalance()+loanApplicationDTO.getAmount());
        accountRepository.save(account.get());
        return new ResponseEntity<>("Loan Approved",HttpStatus.CREATED);
    }

}
