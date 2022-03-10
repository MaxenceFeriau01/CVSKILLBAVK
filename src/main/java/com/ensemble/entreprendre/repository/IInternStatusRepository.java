package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.InternStatus;

public interface IInternStatusRepository extends JpaRepository<InternStatus, Long>, JpaSpecificationExecutor<InternStatus> {

}
