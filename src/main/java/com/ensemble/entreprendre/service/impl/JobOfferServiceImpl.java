package com.ensemble.entreprendre.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.JobOffer;
import com.ensemble.entreprendre.dto.JobOfferDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.repository.IJobOfferRepository;
import com.ensemble.entreprendre.service.IJobOfferService;

@Service
public class JobOfferServiceImpl implements IJobOfferService {

	@Autowired
	IJobOfferRepository jobOfferRepo;
	
	@Autowired
	GenericConverter<JobOffer, JobOfferDto> jobOfferConverter;
	
	@Override
	public Collection<JobOfferDto> getAll() {
		return this.jobOfferConverter.entitiesToDtos(this.jobOfferRepo.findAll(),JobOfferDto.class);
	}

	@Override
	public JobOfferDto getById(long id) throws ApiException {
		return this.jobOfferConverter.entityToDto(this.jobOfferRepo.findById(id).orElseThrow(()-> new ApiException("Cette Offre n'existe pas !",HttpStatus.NOT_FOUND)),JobOfferDto.class);
	}

}
