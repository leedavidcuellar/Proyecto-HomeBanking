package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.dtos.Card;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource
public interface CardReposiroty extends JpaRepository<Card, Long>{
}
