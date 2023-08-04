package com.ensemble.entreprendre.repository;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensemble.entreprendre.domain.Activity;

public interface IActivityRepository extends JpaRepository<Activity, Long>,
		JpaSpecificationExecutor<Activity> {

	@Query("SELECT a.id AS id, a.name AS name, COUNT(DISTINCT c.id) AS companyCount, COUNT(DISTINCT cs.id) AS companySearchCount " +
			"FROM Activity a " +
			"LEFT JOIN a.companies c " +
			"LEFT JOIN a.companiesSearch cs " +
			"WHERE (:name IS NULL OR UPPER(a.name) LIKE %:name%) " +
			"GROUP BY a.id " +
			"ORDER BY name ASC")
	public Page<Tuple> findAllWithCountsByName(Pageable pageable, @Param("name") String name);

}
