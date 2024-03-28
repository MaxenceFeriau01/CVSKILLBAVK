package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.EventLocation;

public interface IEventLocationRepository extends JpaRepository<EventLocation, Long>, JpaSpecificationExecutor<EventLocation> {
}
