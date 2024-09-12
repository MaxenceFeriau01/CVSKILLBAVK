package com.ensemble.entreprendre.repository;

import com.ensemble.entreprendre.domain.PolePersonnaliteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPolePersonnaliteTypeRepository extends JpaRepository<PolePersonnaliteType, Long> {

    Optional<PolePersonnaliteType> findByPersonnaliteType(String PersonnaliteType);
}