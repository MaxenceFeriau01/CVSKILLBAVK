package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ensemble.entreprendre.domain.JobOffer;

public interface IJobOfferRepository extends JpaRepository<JobOffer, Long>, PagingAndSortingRepository<JobOffer, Long>, JpaSpecificationExecutor<JobOffer> {

}
