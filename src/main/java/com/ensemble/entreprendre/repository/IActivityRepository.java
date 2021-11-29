package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ensemble.entreprendre.domain.Activity;

public interface IActivityRepository extends JpaRepository<Activity, Long>, PagingAndSortingRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

}
