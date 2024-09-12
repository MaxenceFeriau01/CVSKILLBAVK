package com.ensemble.entreprendre.service;

import java.util.Collection;

import com.ensemble.entreprendre.dto.InternStatusDto;
import com.ensemble.entreprendre.exception.ApiException;

public interface IInternStatusService {

	Collection<InternStatusDto> findAll() throws ApiException;

}
