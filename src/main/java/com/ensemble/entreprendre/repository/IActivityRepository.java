package com.ensemble.entreprendre.repository;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensemble.entreprendre.domain.Activity;

public interface IActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {
	@Query("SELECT a.id AS id, a.name AS name, COUNT(DISTINCT c.id) AS companyCount, COUNT(DISTINCT cs.id) AS companySearchCount " +
			"FROM Activity a " +
			"LEFT JOIN a.companies c " +
			"LEFT JOIN a.companiesSearch cs " +
			"WHERE (:name IS NULL OR LOWER(a.name) LIKE %:name%) " +
			"GROUP BY a.id " +
			"ORDER BY " +
			"   CASE WHEN :orderCompanySearchCount IS NOT NULL AND :orderCompanySearchCount = 'asc' THEN COUNT(DISTINCT cs.id) END ASC, " +
			"   CASE WHEN :orderCompanySearchCount IS NOT NULL AND :orderCompanySearchCount = 'desc' THEN COUNT(DISTINCT cs.id) END DESC, " +
			"   CASE WHEN :orderCompanyCount IS NOT NULL AND :orderCompanyCount = 'asc' THEN COUNT(DISTINCT c.id) END ASC, " +
			"   CASE WHEN :orderCompanyCount IS NOT NULL AND :orderCompanyCount = 'desc' THEN COUNT(DISTINCT c.id) END DESC, " +
			"   CASE WHEN :orderName IS NOT NULL AND :orderName = 'asc' THEN a.name END ASC, " +
			"   CASE WHEN :orderName IS NOT NULL AND :orderName = 'desc' THEN a.name END DESC, " +
			"   CASE WHEN :orderId IS NOT NULL AND :orderId = 'asc' THEN a.id END ASC, " +
			"   CASE WHEN :orderId IS NOT NULL AND :orderId = 'desc' THEN a.id END DESC")
	public Page<Tuple> findAllWithCountsByName(
			Pageable pageable,
			@Param("name") String name,
			@Param("orderId") String orderId,
			@Param("orderName") String orderName,
			@Param("orderCompanyCount") String orderCompanyCount,
			@Param("orderCompanySearchCount") String orderCompanySearchCount
	);
}
