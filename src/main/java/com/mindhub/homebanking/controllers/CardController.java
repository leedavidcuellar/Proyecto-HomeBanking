package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardReposiroty;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardReposiroty cardReposiroty;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

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
                return new ResponseEntity<>("Card created success 201",HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Client no exists", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    @PostMapping("/cardOperation")
    public ResponseEntity<Object> createCardOperation(@RequestBody com.minduhub.homebanking.dtos.CardOperationDTO cardOperationDTO, Authentication authentication){
        Optional<Card> optionalCard = cardReposiroty.findByNumber(cardOperationDTO.getCardNumber());
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        //verifico que exista la tarjeta
        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>("card doesn't exist", HttpStatus.FORBIDDEN);
        }
        Card searchedCard = optionalCard.get();
        if (!searchedCard.getCvv().equals(cardOperationDTO.getCvv())) {
            return new ResponseEntity<>("CVV wrong", HttpStatus.FORBIDDEN);
        }
        //verifico que los datos no esten vacios
        if (cardOperationDTO.getAmount() <= 0 || cardOperationDTO.getCardNumber().isEmpty() || cardOperationDTO.getCvv().isEmpty() || cardOperationDTO.getDescription().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        //verificar que sea de débito la tarjeta
        if (searchedCard.getType().equals(CardType.CREDIT)){
            return new ResponseEntity<>("You can't do this operation. Use a DEBIT CARD", HttpStatus.FORBIDDEN);
        }
        //verificacion de montos de cuenta, traer el set de cuentas y verificar que el monto de débito sea menor al saldo

        if (accountRepository.findByBalanceGreaterThanAndClient(cardOperationDTO.getAmount(), client).isEmpty()){
            return new ResponseEntity<>("You don't have enough money.", HttpStatus.FORBIDDEN);
        }
        List<Account> accountsWithBalance = accountRepository.findByBalanceGreaterThanAndClient(cardOperationDTO.getAmount(), client);
        //tarjeta vencida
        if(!cardReposiroty.findByThruDateLessThan(LocalDate.now()).isEmpty()){
            return new ResponseEntity<>("You're card is expired", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(cardOperationDTO.getAmount(), cardOperationDTO.getDescription(), LocalDateTime.now(),accountsWithBalance.get(0),TransactionType.DEBIT);
        transactionRepository.save(debitTransaction);

        return new ResponseEntity<>("You payed something", HttpStatus.CREATED);
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
