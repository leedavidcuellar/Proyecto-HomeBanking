package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getClientLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/loans/{id}")
    public LoanDTO getLoan(@PathVariable Long id){
        return loanRepository.findById(id).map(LoanDTO::new).orElse(null);
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Client client  = clientRepository.findByEmail(authentication.getName()).get();// veo cliente es el que se logueo
        Optional<Loan> loan=loanRepository.findById(loanApplicationDTO.getLoanId());// prestamo que nos pasan
        if(!loan.isPresent()){//veo si esta en base
            return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
        }
        Optional<Account> account= accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        if(!account.isPresent()){// la cuenta existe
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
        }
        //veo que los pagos esten, nuemero y id
        if(loanApplicationDTO.getLoanId()<=0||loanApplicationDTO.getToAccountNumber().isEmpty()|| loanApplicationDTO.getPayments()==0) {
            return new ResponseEntity<>("This payments doesn't exist for this loan", HttpStatus.FORBIDDEN);
        }

        //pagos se correspondan
        if (!loan.get().getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("This number of payments is not enabled fot this loan", HttpStatus.FORBIDDEN);
        }

        //si corresponde los montos maximos
        if (loan.get().getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("This amount is bigger to this loan", HttpStatus.FORBIDDEN);
        }

        //cliente tiene esa cuenta.
        if(client.getAccount().stream().noneMatch(account1 -> account1.getNumber().equals(loanApplicationDTO.getToAccountNumber()))) {
            return new ResponseEntity<>("This account does not belong to you",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loan.get(),loanApplicationDTO.getAmount()*1.20, loanApplicationDTO.getPayments(), client);
        clientLoanRepository.save(clientLoan);
        Transaction transaction = new Transaction(loanApplicationDTO.getAmount(), loan.get().getName()+" Loan Approved", LocalDateTime.now(), account.get(), TransactionType.CREDIT);
        transactionRepository.save(transaction);
        accountRepository.save(account.get());
        return new ResponseEntity<>("Loan Approved",HttpStatus.CREATED);
    }

}
