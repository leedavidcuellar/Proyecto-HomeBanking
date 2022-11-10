package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Optional<Client> optionalClient = clientRepository.findByEmail(authentication.getName());
        if (optionalClient.isPresent()) {
            return optionalClient.get().getAccount().stream().map(AccountDTO::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    //cuando podemos filtrar y sacar segun la info que necesitamos trae todas las cuentas y luego filtra
    @GetMapping("/accounts/greatThan/{importe}")
    public List<AccountDTO> getAccountsGreatThan(@PathVariable double importe) {
        return accountRepository.findAll()
                .stream()
                .filter(account -> account.getBalance() > importe)
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    //otra forma mas optima porque reduce tiempo ejecucion porque es especifico primero filtra y despues trae cuentas
    //-Buscar una lista de cuentas en el cual su balance se mayor a x monto pasado por parametro
    @GetMapping("/accounts/greatThanMount/{importe}")
    public List<AccountDTO> getAccountsGreatThanV(@PathVariable double importe) {
        return accountRepository.findByBalanceGreaterThan(importe)
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @PostMapping("/clients/current/accounts") //localhost:8080/api/clients/current/accounts
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        try {
            Optional<Client> optionalClient = clientRepository.findByEmail(authentication.getName());
            if (optionalClient.isPresent()) {
                if (optionalClient.get().getAccount().size() < 3) {
                    Account account = new Account(optionalClient.get());
                    accountRepository.save(account);
                    return new ResponseEntity<>("201 Created", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("403 Forbidden", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Client No Exists", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // -Buscar una lista de cuentas por en la cual sue fecha se menor a la que le pasemos por parametro
    @GetMapping("/accounts/CreationDateLessThan/{date}")
    public List<AccountDTO> getByCreationDateLessThan(@PathVariable String date) {//
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println(LocalDateTime.parse(date, formatter));
        return accountRepository.findByCreationDateLessThan(LocalDateTime.parse(date, formatter))
                .stream()
                .map(account -> new AccountDTO(account.get()))
                .collect(Collectors.toList());
    }

    // -Buscar una cuenta por Numero de cuenta
    @GetMapping("/accounts/byNumber/{number}")
    public AccountDTO getAccount(@PathVariable String number) {
        return accountRepository.findByNumber(number).map(AccountDTO::new).orElse(null);
    }
}
