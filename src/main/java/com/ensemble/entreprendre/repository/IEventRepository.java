package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.Event;

public interface IEventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
}
