package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    //  -Buscar una lista de ClientLoan por cliente
    List<ClientLoan> findByClient_Id(Long id);

    // -Buscar una lista de ClientLoan que sean mayores a x monto pasado por parametro
    List<ClientLoan> findByAmountGreaterThan(double amount);

    //-Buscar una lista de ClientLoan por cliente que  en cual sus balances sean menores a x monto pasado por parametro
    List<ClientLoan> findByClient_IdAndAmountLessThan(Long id, double amount);

}
