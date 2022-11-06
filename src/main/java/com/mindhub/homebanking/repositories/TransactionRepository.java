package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByAccount_Number(String number);

    //-Buscar una lista de transacciones entre dos fechas pasadas por parametro
    List<Transaction> findByDateBetween(LocalDateTime date1, LocalDateTime date2);

    // -Buscar una lista de transacciones en las cuales el monto se mayor a x monto pasado como primer parametro,y menor a x monto  pasado como segundo parametro
   List<Transaction> findByAmountGreaterThanAndAmountLessThan(double amount1, double amount2);

    //-Buscar una lista de transacciones por type
    List<Transaction> findByType(TransactionType type);
}
