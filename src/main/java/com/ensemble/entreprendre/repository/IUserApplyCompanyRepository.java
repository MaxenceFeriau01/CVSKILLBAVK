package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.UserApplyCompany;

public interface IUserApplyCompanyRepository extends JpaRepository<UserApplyCompany, Long>, JpaSpecificationExecutor<UserApplyCompany> {
}
