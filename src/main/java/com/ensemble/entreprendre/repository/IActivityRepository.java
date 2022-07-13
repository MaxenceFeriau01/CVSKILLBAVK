package com.ensemble.entreprendre.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ensemble.entreprendre.domain.Activity;
import com.ensemble.entreprendre.projection.CustomActivity;

public interface IActivityRepository extends JpaRepository<Activity, Long>,
		JpaSpecificationExecutor<Activity> {

	@Query("SELECT new com.ensemble.entreprendre.projection.CustomActivity(a.id as id, a.name as name, count(c) as companyCount, count(u) as userCount, count(s) as companySearchCount) "
			+ "FROM Activity a left join a.companies c " + "left join a.users u " + "left join a.companiesSearch s "
			+ "WHERE UPPER(a.name) like %:#{#name}% GROUP BY a.id ORDER BY name ASC")
	public Page<CustomActivity> findAllWithCountsByName(Pageable pageable, String name);

}
