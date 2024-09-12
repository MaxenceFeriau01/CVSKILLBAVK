package com.ensemble.entreprendre.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensemble.entreprendre.domain.InternType;

public interface IInternTypeRepository extends JpaRepository<InternType, Long>, JpaSpecificationExecutor<InternType> {

    @Query(value = "SELECT COUNT(*) FROM interns_type WHERE '2 à 6 mois' = ANY(periods) AND created_date >= :startedAt AND created_date <= :endedAt", nativeQuery = true)
    public Long countAllBetweenPeriod(@Param("startedAt") LocalDateTime startedAt, @Param("endedAt") LocalDateTime endedAt);

}