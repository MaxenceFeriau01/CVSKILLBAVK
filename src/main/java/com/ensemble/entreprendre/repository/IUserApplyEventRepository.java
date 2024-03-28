package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensemble.entreprendre.domain.UserApplyEvent;

public interface IUserApplyEventRepository extends JpaRepository<UserApplyEvent, Long> {
}
