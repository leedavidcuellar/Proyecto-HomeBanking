package com.mindhub.homebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client>findByLastNameIgnoreCase(String lastName);

    Client findByFirstNameAndLastNameIgnoreCase(String firstname,String lastName);
    
    List<Client> findByFirstNameIgnoreCaseOrderByLastNameAsc(String firstName);
}//con el nombre de las variables al lado del findby, lo busca sin hacer query y ignorecase, no importa mayuscula o minuscula
