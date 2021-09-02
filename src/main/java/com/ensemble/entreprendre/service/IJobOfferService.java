package com.ensemble.entreprendre.service;

import java.util.Collection;

import com.ensemble.entreprendre.dto.JobOfferDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;

public interface IJobOfferService {

	Collection<JobOfferDto> getAll();

	JobOfferDto getById(long id) throws ApiException;

}
