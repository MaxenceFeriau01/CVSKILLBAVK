package com.ensemble.entreprendre.repository;

import com.ensemble.entreprendre.domain.PoleAtout;
import com.ensemble.entreprendre.domain.PoleInteret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPoleInteretRepository extends JpaRepository<PoleInteret, Long> , JpaSpecificationExecutor<PoleInteret> {
    Optional<PoleInteret> findByInteret(String Interet);
}