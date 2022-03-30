package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensemble.entreprendre.domain.Role;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;

public interface IRoleRepository extends JpaRepository<Role, Long> {
	    Role findByRole(RoleEnum role);
	}

