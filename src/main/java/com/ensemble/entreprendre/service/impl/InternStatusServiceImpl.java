package com.ensemble.entreprendre.service.impl;

import java.util.Collection;

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

	@Autowired
	IInternStatusRepository statusRepository;

	@Autowired
	GenericConverter<InternStatus, InternStatusDto> statusConverter;

	@Override
	public Collection<InternStatusDto> findAll() throws ApiException {
		return this.statusConverter.entitiesToDtos(this.statusRepository.findAll(
				Sort.by(Sort.Direction.ASC, InternStatus_.NAME)), InternStatusDto.class);
	}

	@Override
	public InternStatusDto findById(long id) throws ApiException {
		return this.statusConverter.entityToDto(this.statusRepository.findById(id)
				.orElseThrow(() -> new ApiException("Ce statut de stagiaire n'existe pas.")), InternStatusDto.class);
	}

}
