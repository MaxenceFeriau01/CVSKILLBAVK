package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.InternType;

public interface IInternTypeRepository extends JpaRepository<InternType, Long>, JpaSpecificationExecutor<InternType> {

}
