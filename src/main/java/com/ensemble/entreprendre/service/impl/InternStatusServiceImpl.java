package com.ensemble.entreprendre.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.InternStatus;
import com.ensemble.entreprendre.domain.InternStatus_;
import com.ensemble.entreprendre.dto.InternStatusDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.repository.IInternStatusRepository;
import com.ensemble.entreprendre.service.IInternStatusService;

@Service
public class InternStatusServiceImpl implements IInternStatusService {

	private static final String JOB_SEEKER_STATUS = "Demandeur d'emploi";

	@Autowired
	IInternStatusRepository statusRepository;

	@Autowired
	GenericConverter<InternStatus, InternStatusDto> statusConverter;

	@Override
	public Collection<InternStatusDto> findAll() throws ApiException {
		return this.statusConverter.entitiesToDtos(this.statusRepository.findAll(
				Sort.by(Sort.Direction.ASC, InternStatus_.NAME)), InternStatusDto.class);
	}

	/**
	 * Does not return the JobSeeker status for applicant
	 * 
	 * @return
	 * @throws ApiException
	 */
	@Override
	public Collection<InternStatusDto> findAllForApplicant() throws ApiException {

		List<InternStatus> internStatus = this.statusRepository.findAll(
				Sort.by(Sort.Direction.ASC, InternStatus_.NAME));
		internStatus = internStatus.stream().filter(status -> !status.getName().equals(
				JOB_SEEKER_STATUS))
				.collect(Collectors.toList());

		return this.statusConverter.entitiesToDtos(internStatus, InternStatusDto.class);
	}

	@Override
	public InternStatusDto findById(long id) throws ApiException {
		return this.statusConverter.entityToDto(this.statusRepository.findById(id)
				.orElseThrow(() -> new ApiException("Ce statut de stagiaire n'existe pas.")), InternStatusDto.class);
	}

}
