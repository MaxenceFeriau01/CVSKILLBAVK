package com.ensemble.entreprendre.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ensemble.entreprendre.domain.Job;
import com.ensemble.entreprendre.projection.CustomJob;

public interface IJobRepository
		extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

	@Query("SELECT new com.ensemble.entreprendre.projection.CustomJob(a.id as id, a.name as name, count(c) as companyCount, count(u) as userCount) "
			+ "FROM Job a left join a.companies c " + "left join a.users u "
			+ "WHERE UPPER(a.name) like %:#{#name}% GROUP BY a.id ORDER BY name ASC")
	public Page<CustomJob> findAllWithCountsByName(Pageable pageable, String name);
}
