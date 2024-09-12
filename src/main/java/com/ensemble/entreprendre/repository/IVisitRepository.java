package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.Visit;

public interface IVisitRepository extends JpaRepository<Visit, Long>, JpaSpecificationExecutor<Visit> {
}
