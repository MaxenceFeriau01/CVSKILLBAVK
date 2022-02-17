package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ensemble.entreprendre.domain.Job;

public interface IJobRepository extends JpaRepository<Job, Long>, PagingAndSortingRepository<Job, Long>, JpaSpecificationExecutor<Job> {

}
