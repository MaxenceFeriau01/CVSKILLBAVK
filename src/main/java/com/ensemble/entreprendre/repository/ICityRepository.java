package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ensemble.entreprendre.domain.City;

public interface ICityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

}
