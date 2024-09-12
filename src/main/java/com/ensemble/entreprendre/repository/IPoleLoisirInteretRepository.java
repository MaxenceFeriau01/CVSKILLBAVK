package com.ensemble.entreprendre.repository;

import com.ensemble.entreprendre.domain.PoleLoisirInteret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPoleLoisirInteretRepository extends JpaRepository<PoleLoisirInteret, Long> {
    Optional<PoleLoisirInteret> findByNameAndType(String name, PoleLoisirInteret.PoleLoisirInteretType type);
}