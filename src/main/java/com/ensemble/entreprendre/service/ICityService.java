package com.ensemble.entreprendre.service;

import java.util.Collection;

import com.ensemble.entreprendre.dto.CityDto;
import com.ensemble.entreprendre.exception.ApiException;

public interface ICityService {

	Collection<CityDto> findAll() throws ApiException;

}
