package com.ensemble.entreprendre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.JobOfferDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.JobOfferDtoFilter;

public interface IJobOfferService {

	Page<JobOfferDto> getAll(Pageable pageable, JobOfferDtoFilter filter);

	JobOfferDto getById(long id) throws ApiException;

	JobOfferDto create(JobOfferDto toCreate) throws ApiException;

}
