package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);// -Buscar una cuenta por Numero de cuenta

    //opciones pedir cuentas mas 3mil pesos //-Buscar una lista de cuentas en el cual su balance se mayor a x monto pasado por parametro
    List<Account> findByBalanceGreaterThan(double balance);

    // -Buscar una lista de cuentas por en la cual sue fecha se menor a la que le pasemos por parametro
    List<Optional<Account>> findByCreationDateLessThan(LocalDateTime creationDate);

    List<Account> findByBalanceGreaterThanAndClient(double amount, Client client);
}
