package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.Card;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.repositories.CardReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardReposiroty cardReposiroty;

    @GetMapping("/cards")
    public List<Card> getCards(){return cardReposiroty.findAll();}

    @GetMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardReposiroty.findById(id).map(CardDTO::new).orElse(null);
    }
}
