package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardReposiroty;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.hamcrest.Matcher;
import org.hamcrest.text.CharSequenceLength;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import utils.CardUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardReposiroty cardReposiroty;

    @Autowired
    AccountRepository accountRepository;

    // Test Loans
    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }


    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    //Test Client Controller
    @Test
    public void containsExitsMailChart(){
        String mail = clientRepository.findById(1L).get().getEmail();
        assertThat(mail,containsString("@"));
    }

    @Test
    public void existClientsEmail(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, everyItem(hasProperty("email", not(emptyString()))));
    }

    // Test Card
    @Test
    public void cardCvvIsCreated(){
        List<Card> cards = cardReposiroty.findAll();
        assertThat(cards,everyItem(hasProperty("cvv",is(not(nullValue())))));
    }

    @Test
    public void cardNumberIsDiferent(){
        List<Card> cards = cardReposiroty.findAll();
        assertThat(cards,everyItem(hasProperty("number",notNullValue(Integer.class))));
    }

    // Test Account
    @Test
    public void balanceIsGreatThanzero(){
        List<Account> accounts = accountRepository.findByBalanceGreaterThan(0);
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void createdDateLessThanNow(){
        String date = "2022-11-16 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Optional<Account>> accounts = accountRepository.findByCreationDateLessThan(LocalDateTime.parse(date, formatter));
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void cardsExpired() {
        List<Card> cards = cardReposiroty.findAll();
        assertThat(cards, everyItem(hasProperty("thruDate", greaterThan(LocalDate.now()))));
    }

    //Test unitarios
    @Test
    public void generateRandomNumber(){
        String cardNumber = CardUtils.generateNumberAleatorio(6);
        assertThat(cardNumber, CharSequenceLength.hasLength(6));
    }

}

