package com.ensemble.entreprendre.repository;

import com.ensemble.entreprendre.domain.PoleAtout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPoleAtoutRepository extends JpaRepository<PoleAtout, Long>, JpaSpecificationExecutor<PoleAtout> {

    Optional<PoleAtout> findByAtout(String atout);
}