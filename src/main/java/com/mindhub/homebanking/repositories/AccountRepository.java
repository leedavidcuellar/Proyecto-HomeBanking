package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByNumber(String number);

    //opciones pedir cuentas mas 3mil pesos
    List<Account> findByBalanceGreaterThan(double balance);

    //OrderByNumber
}
