package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RepositoryRestResource
public interface CardReposiroty extends JpaRepository<Card, Long>{

    List<Card> findByClient_Id(Long id);



}
