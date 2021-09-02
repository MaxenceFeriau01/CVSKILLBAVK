package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensemble.entreprendre.domain.JobOffer;

public interface IJobOfferRepository extends JpaRepository<JobOffer, Long> {

}
