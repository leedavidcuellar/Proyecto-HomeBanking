package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import utils.CardUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).toList();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
            return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @GetMapping("/clients/lastNam/{lastName}")
    public ClientDTO getClientByLastName(@PathVariable String lastName){
        return clientRepository.findByLastNameIgnoreCase(lastName).map(ClientDTO::new).orElse(null);
    }

    @GetMapping("/clients/firstName/{firstName}") //como es lista no usamos orelse null porque se usa stream
    public List<ClientDTO> getClientsByFirstName(@PathVariable String firstName){
        return clientRepository.findByLastNameIgnoreCase(firstName).stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clients/email/{email}")
    public ClientDTO getClientsByEmail(@PathVariable String email){
        return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN); // por si datos vienen bavacios
        }

        if(clientRepository.findByEmail(email).isPresent()){ //corroboro que no haya mail repetido
            return new ResponseEntity<>("UserName alreday exists",HttpStatus.FORBIDDEN);
        }
        try {
            //genero un cliente
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
            clientRepository.save(client);
            //corroboro numero cuenta
            Random aleatorio = new Random();
            String aux = "";
            do {
                aux = "VIN - " + CardUtils.generateNumberAleatorio(8);
            } while (accountRepository.findByNumber(aux).isPresent());
            //genero una cuenta
            accountRepository.save(new Account(aux, LocalDateTime.now(), 0.00, client));
            // Para indicar que se guardo.
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clients/{clientId}/accounts") //localhost:8080/api/clients/1/accounts
    public ResponseEntity<Object> createAccount(@PathVariable Long clientId){
       try {
           Optional<Client> optionalClient = clientRepository.findById(clientId);
           if (optionalClient.isPresent()) {
               Account account;
               do {
                    account = new Account(optionalClient.get());
               }while(accountRepository.findByNumber(account.getNumber()).isPresent());
                    accountRepository.save(account);
                   return new ResponseEntity<>(HttpStatus.CREATED);
           } else {
               return new ResponseEntity<>("Cliente no exists", HttpStatus.FORBIDDEN);
           }
       }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
    Client client = clientRepository.findByEmail(authentication.getName()).get();
    return new ClientDTO(client);
    }

}

/*
Account account;//declaro la variable account
        do{
            account = new Account(LocalDateTime.now(),0.00,client);
        }while(accountRepository.findByNumber(account.getNumber()).isPresent());
        //comprobar que no este repetido
        //el numero de cuenta, si lo está realiza nuevamente el ciclo


        try {
            Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
            Account account;
            int accounts = accountRepository.findAll().size();
            if (accounts < 999) {
                do {
                    account = new Account("VIN" + random3digits(), LocalDateTime.now(), 0, client);
                } while (accountRepository.findByNumber(account.getNumber()).isPresent());

                accountRepository.save(account);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("No more available accounts", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
 */