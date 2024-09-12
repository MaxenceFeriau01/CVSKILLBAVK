package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.Company;

public interface ICompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

}
