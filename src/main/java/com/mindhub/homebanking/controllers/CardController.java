package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardReposiroty;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardReposiroty cardReposiroty;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/cards")
    public List<Card> getCards(){return cardReposiroty.findAll();}

    @GetMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardReposiroty.findById(id).map(CardDTO::new).orElse(null);
    }

    @GetMapping("/clients/{id}/cards/")
    public List<CardDTO> getClientCardById(@PathVariable Long id){
        /*return cardReposiroty.findByClient_Id(id)
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());*/
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isPresent()){
            return optionalClient.get().getCards().stream().map(CardDTO::new).collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }


}
