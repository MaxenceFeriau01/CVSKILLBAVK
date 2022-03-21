package com.ensemble.entreprendre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.exception.ApiNotFoundException;

import net.bytebuddy.utility.RandomString;

public interface IUserRepository
		extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	Optional<User> findByEmail(String email);

	Optional<User> findByResetPasswordToken(String token);
}
