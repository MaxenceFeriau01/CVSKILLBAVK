package com.ensemble.entreprendre.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.InternType;
import com.ensemble.entreprendre.dto.InternStatusDto;
import com.ensemble.entreprendre.dto.InternTypeDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.repository.IInternTypeRepository;
import com.ensemble.entreprendre.service.IInternTypeService;

@Service
public class InternTypeServiceImpl implements IInternTypeService {

	@Autowired
	IInternTypeRepository typeRepository;

	@Autowired
	GenericConverter<InternType, InternTypeDto> typeConverter;
	
	@Override
	public Collection<InternTypeDto> findAll() throws ApiException {
		return this.typeConverter.entitiesToDtos(this.typeRepository.findAll(), InternTypeDto.class);
	}

	@Override
	public InternTypeDto findById(long id) throws ApiException {
		return this.typeConverter.entityToDto(this.typeRepository.findById(id)
				.orElseThrow(() -> new ApiException("Ce statut de stagiaire n'existe pas.")), InternTypeDto.class);
	}

}
