package com.ensemble.entreprendre.service;

import java.util.Collection;

import com.ensemble.entreprendre.dto.InternTypeDto;
import com.ensemble.entreprendre.exception.ApiException;

public interface IInternTypeService {

	Collection<InternTypeDto> findAll() throws ApiException;
	
	InternTypeDto findById(long id) throws ApiException;
	
}
