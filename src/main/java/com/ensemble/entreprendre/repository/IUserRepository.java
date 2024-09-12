package com.ensemble.entreprendre.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;

public interface IUserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);

    Collection<User> findByRoles_Role(RoleEnum role);
}
