package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

//extra probar ejercicio
public interface BankRepository extends JpaRepository<Bank, Long> {
}
