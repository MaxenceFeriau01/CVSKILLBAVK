package com.ensemble.entreprendre.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensemble.entreprendre.domain.Job;

public interface IJobRepository
		extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

	@Query("SELECT j.id AS id, j.name AS name, COUNT(DISTINCT c.id) AS companyCount, " +
			"COUNT(DISTINCT u.id) AS userCount " +
			"FROM Job j " +
			"LEFT JOIN j.companies c " +
			"LEFT JOIN j.users u " +
			"WHERE (:name IS NULL OR UPPER(j.name) LIKE %:name%) " +
			"GROUP BY j.id " +
			"ORDER BY name ASC")
	public Page<Tuple> findAllWithCountsByName(Pageable pageable, @Param("name") String name);

	@Query("SELECT j.id AS id, j.name AS name, COUNT(DISTINCT u.id) AS userCount " +
			"FROM Job j " +
			"LEFT JOIN j.users u " +
			"GROUP BY j.id " +
			"ORDER BY userCount DESC")
	public List<Tuple> findAllWithUserCountOrderByUserCountDesc();

}
