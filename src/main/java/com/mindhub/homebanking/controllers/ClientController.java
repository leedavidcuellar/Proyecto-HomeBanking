package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

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

}
