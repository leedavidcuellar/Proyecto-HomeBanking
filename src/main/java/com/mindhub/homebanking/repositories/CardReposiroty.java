package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CardReposiroty extends JpaRepository<Card, Long>{

    List<Card> findByClient_Id(Long id);

    Optional<Card> findByNumber(String number);

    List<Card> findByThruDateLessThan(LocalDate ThruDate);

}
