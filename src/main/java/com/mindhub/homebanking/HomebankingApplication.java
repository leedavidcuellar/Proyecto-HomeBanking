package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
/*
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardReposiroty cardReposiroty){
		return (args) -> {
			Client cliente1 = new Client("Pedro", "Sanchez", "pedro@pedro.com",passwordEncoder.encode("1234"));
			clientRepository.save(cliente1);
			Client cliente = new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("1234"));
			clientRepository.save(cliente);
			Client cliente2 = new Client("admin","admin","admin@admin.com", passwordEncoder.encode("1234"));
			clientRepository.save(cliente2);

			Account account1 = new Account("VIN001", LocalDateTime.now() ,5000.00,cliente1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1) ,7500.00,cliente1);
			accountRepository.save(account2);
			Account account3 = new Account("VIN003", LocalDateTime.now().plusDays(1) ,3500.00,cliente);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004", LocalDateTime.now() ,7000.00,cliente);
			accountRepository.save(account4);
			Account account5 = new Account(cliente);
			accountRepository.save(account5);


			Transaction transaction1 = new Transaction(1500.00,"aB", LocalDateTime.now(),account1, TransactionType.CREDIT);
			Transaction transaction2 = new Transaction(500.00,"aB1", LocalDateTime.now().plusDays(1),account1, TransactionType.CREDIT);
			Transaction transaction3 = new Transaction(-500.00,"aBc", LocalDateTime.now(),account1, TransactionType.DEBIT);
			Transaction transaction4 = new Transaction(-2000.00,"aBc1", LocalDateTime.now(),account1, TransactionType.DEBIT);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(3000.00,"Ba", LocalDateTime.now(),account2, TransactionType.CREDIT);
			Transaction transaction6 = new Transaction(1000.00,"Ba1", LocalDateTime.now().plusDays(1),account2, TransactionType.CREDIT);
			Transaction transaction7 = new Transaction(-1000.00,"Bca", LocalDateTime.now(),account2, TransactionType.DEBIT);
			Transaction transaction8 = new Transaction(-1000.00,"Bca1", LocalDateTime.now(),account2, TransactionType.DEBIT);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);

			Transaction transaction9 = new Transaction(4000.00,"Cd", LocalDateTime.now(),account3, TransactionType.CREDIT);
			Transaction transaction10 = new Transaction(1000.00,"Cd1", LocalDateTime.now().plusDays(1),account3, TransactionType.CREDIT);
			Transaction transaction11 = new Transaction(-1000.00,"Cde", LocalDateTime.now(),account3, TransactionType.DEBIT);
			Transaction transaction12 = new Transaction(-1000.00,"Cde1", LocalDateTime.now(),account3, TransactionType.DEBIT);
			transactionRepository.save(transaction9);
			transactionRepository.save(transaction10);
			transactionRepository.save(transaction11);
			transactionRepository.save(transaction12);

			Transaction transaction13 = new Transaction(5000.00,"Dc", LocalDateTime.now(),account4, TransactionType.CREDIT);
			Transaction transaction14 = new Transaction(1000.00,"Dc1", LocalDateTime.now().plusDays(1),account4, TransactionType.CREDIT);
			Transaction transaction15 = new Transaction(-1000.00,"Edc", LocalDateTime.now().plusDays(2),account4, TransactionType.DEBIT);
			Transaction transaction16 = new Transaction(-2000.00,"Edc1", LocalDateTime.now().plusDays(3),account4, TransactionType.DEBIT);
			transactionRepository.save(transaction13);
			transactionRepository.save(transaction14);
			transactionRepository.save(transaction15);
			transactionRepository.save(transaction16);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Loan loan1 = new Loan("Hipotecario",500000.00, List.of(12,24,36,48,60));
			loanRepository.save(loan1);
			Loan loan2 = new Loan("Personal",100000.00, List.of(6,12,24));
			loanRepository.save(loan2);
			Loan loan3 = new Loan("Automotriz",300000.00, List.of(6,12,24,36));
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(loan1,400000,60,cliente1);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(loan2,50000,12,cliente1);
			clientLoanRepository.save(clientLoan2);
			ClientLoan clientLoan3 = new ClientLoan(loan2,100000,24,cliente);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(loan3,200000,36,cliente);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card("4073-8985-8746-1567", CardColor.GOLD,CardType.DEBIT, LocalDate.now(),cliente);
			cardReposiroty.save(card1);
			Card card2 = new Card("5473-8985-7846-4765", CardColor.TITANIUM,CardType.CREDIT,LocalDate.now(),cliente);
			cardReposiroty.save(card2);
			Card card3 = new Card("4573-8985-4687-2543", CardColor.SILVER,CardType.CREDIT,LocalDate.now(),cliente1);
			cardReposiroty.save(card3);
			Card card4 = new Card("4071-8985-4867-5432", CardColor.GOLD,CardType.DEBIT,LocalDate.now(),cliente1);
			cardReposiroty.save(card4);

		};
	}*/
}
