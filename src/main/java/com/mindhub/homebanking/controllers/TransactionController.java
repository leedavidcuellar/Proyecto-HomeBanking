package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

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

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createdTransactionBetweenAccounts(Authentication authentication, @RequestParam Double amount, @RequestParam String description,
                                                                    @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber) {
        if (amount == 0 || description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("You dont can transfer to your same account", HttpStatus.FORBIDDEN);
        }
        Optional<Account> sendingAccount = accountRepository.findByNumber(fromAccountNumber);
        if (!sendingAccount.isPresent()) {
            return new ResponseEntity<>("Origin Account no exist", HttpStatus.FORBIDDEN);
        }
        Optional<Client> client = clientRepository.findByEmail(sendingAccount.get().getClient().getEmail());
        if (!client.isPresent()) {
            return new ResponseEntity<>("You cant do this operation", HttpStatus.FORBIDDEN);
        }
        Optional<Account> receiverAccount = accountRepository.findByNumber(toAccountNumber);
        if (!receiverAccount.isPresent()) {
            return new ResponseEntity<>("the receiver account no exist, please check number", HttpStatus.FORBIDDEN);
        }
        if (sendingAccount.get().getBalance() < amount) {
            return new ResponseEntity<>("You Exceded amount avaiable", HttpStatus.FORBIDDEN);
        }

       // sendingAccount.get().setBalance(sendingAccount.get().getBalance() - amount);
       // receiverAccount.get().setBalance(receiverAccount.get().getBalance() + amount);

        Transaction transaction1 = new Transaction(-(amount), description + "-" + sendingAccount.get().getNumber(), LocalDateTime.now(), sendingAccount.get(),TransactionType.DEBIT);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction(amount, description + "-" + receiverAccount.get().getNumber(), LocalDateTime.now(), receiverAccount.get(),TransactionType.CREDIT);
        transactionRepository.save(transaction2);

        accountRepository.save(sendingAccount.get());
        accountRepository.save(receiverAccount.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
