package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardReposiroty;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
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
    public List<Card> getCards() {
        return cardReposiroty.findAll();
    }

    @GetMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id) {
        return cardReposiroty.findById(id).map(CardDTO::new).orElse(null);
    }

    @GetMapping("/clients/{id}/cards/")
    public List<CardDTO> getClientCardById(@PathVariable Long id) {
        /*return cardReposiroty.findByClient_Id(id)
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());*/
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return optionalClient.get().getCards().stream().map(CardDTO::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/clients/{clientId}/card")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, @PathVariable Long clientId) {
        try {
            //Ojo! El cliente no puede tener mas de tres Cards del mismo cardType
            //Si tiene mas de tres
            //return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            Optional<Client> optionalClient = clientRepository.findById(clientId);
            if (optionalClient.isPresent()) {
                if (optionalClient.get().getCards().stream().filter(card -> card.getType() == cardType).count() < 3) {
                    cardReposiroty.save(new Card(cardColor, cardType, optionalClient.get()));
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("No puede crear màs de 3 tarjetas del mismo tipo. ", HttpStatus.INTERNAL_SERVER_ERROR); //avisa que ya no hay cuentas disponbles
                }

            } else {
                return new ResponseEntity<>("Cliente no exists", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ex) { //si falla algo de arriba, devuelve este error
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR); //error inesperado
        }

    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication) {
        Optional<Client> optionalClient = clientRepository.findByEmail(authentication.getName());
        if (optionalClient.isPresent()) {
            if (optionalClient.get().getCards().stream().filter(card -> card.getType() == cardType).count() < 3) {
                cardReposiroty.save(new Card(cardColor, cardType, optionalClient.get()));
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Cliente no exists", HttpStatus.FORBIDDEN);
        }
    }
}


/*
                int contador = 0;

                for (Card card : optionalClient.get().getCards()) {

                    if (card.getType() == cardType) {
                        contador++;
                    }
                }
                if (contador <= 2) {
                    cardReposiroty.save(new Card(cardColor, cardType, optionalClient.get()));
                    return new ResponseEntity<>(HttpStatus.CREATED); //devuelve un created
                } else {
                    return new ResponseEntity<>("No puede crear màs de 3 tarjetas del mismo tipo. ", HttpStatus.INTERNAL_SERVER_ERROR); //avisa que ya no hay cuentas disponbles
                }
                */
