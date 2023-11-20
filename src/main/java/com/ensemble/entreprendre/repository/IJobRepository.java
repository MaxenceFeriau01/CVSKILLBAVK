package com.ensemble.entreprendre.repository;

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

	@Query("SELECT j.id AS id, j.name AS name, COUNT(DISTINCT c.id) AS companyCount, COUNT(DISTINCT u.id) AS userCount " +
			"FROM Job j " +
			"LEFT JOIN j.companies c " +
			"LEFT JOIN j.users u " +
			"WHERE (:name IS NULL OR LOWER(j.name) LIKE %:name%) " +
			"GROUP BY j.id, j.name " +
			"ORDER BY " +
			"   CASE WHEN :orderUserCount IS NOT NULL AND :orderUserCount = 'asc' THEN COUNT(DISTINCT u.id) END ASC, " +
			"   CASE WHEN :orderUserCount IS NOT NULL AND :orderUserCount = 'desc' THEN COUNT(DISTINCT u.id) END DESC, " +
			"   CASE WHEN :orderCompanyCount IS NOT NULL AND :orderCompanyCount = 'asc' THEN COUNT(DISTINCT c.id) END ASC, " +
			"   CASE WHEN :orderCompanyCount IS NOT NULL AND :orderCompanyCount = 'desc' THEN COUNT(DISTINCT c.id) END DESC, " +
			"   CASE WHEN :orderName IS NOT NULL AND :orderName = 'asc' THEN j.name END ASC, " +
			"   CASE WHEN :orderName IS NOT NULL AND :orderName = 'desc' THEN j.name END DESC, " +
			"   CASE WHEN :orderId IS NOT NULL AND :orderId = 'asc' THEN j.name END ASC, " +
			"   CASE WHEN :orderId IS NOT NULL AND :orderId = 'desc' THEN j.name END DESC")
    Page<Tuple> findAllWithCountsByName(
            Pageable pageable,
            @Param("name") String name,
            @Param("orderId") String orderId,
            @Param("orderName") String orderName,
            @Param("orderUserCount") String orderUserCount,
            @Param("orderCompanyCount") String orderCompanyCount
    );

	@Query(value = "SELECT j.name AS name, COUNT(DISTINCT u.id) AS userCount " +
			"FROM Job j " +
			"LEFT JOIN j.users u " +
			"WHERE (:name is null OR LOWER(j.name) like %:name%) " +
			"GROUP BY j.name " +
			"ORDER BY " +
			"   CASE WHEN :orderUserCount IS NOT NULL AND :orderUserCount = 'asc' THEN COUNT(DISTINCT u.id) END ASC, " +
			"   CASE WHEN :orderUserCount IS NOT NULL AND :orderUserCount = 'desc' THEN COUNT(DISTINCT u.id) END DESC, " +
			"   CASE WHEN :orderName IS NOT NULL AND :orderName = 'asc' THEN j.name END ASC, " +
			"   CASE WHEN :orderName IS NOT NULL AND :orderName = 'desc' THEN j.name END DESC")
    Page<Tuple> findJobsWithUserCount(
            Pageable pageable,
            @Param("name") String name,
            @Param("orderName") String orderName,
            @Param("orderUserCount") String orderUserCount
    );
}
