package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    //cuando podemos filtrar y sacar segun la info que necesitamos trae todas las cuentas y luego filtra
    @GetMapping("/accounts/greatThan/{importe}")
    public List<AccountDTO> getAccountsGreatThan(@PathVariable double importe){
        return accountRepository.findAll()
                .stream()
                .filter(account -> account.getBalance()>importe)
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    //otra forma mas optima porque reduce tiempo ejecucion porque es especifico primero filtra y despues trae cuentas
    @GetMapping("/accounts/greatThanMount/{importe}")
    public List<AccountDTO> getAccountsGreatThanV(@PathVariable double importe){
        return accountRepository.findByBalanceGreaterThan(importe)
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
}
