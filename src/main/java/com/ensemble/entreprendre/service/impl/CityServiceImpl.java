package com.ensemble.entreprendre.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.City;
import com.ensemble.entreprendre.domain.City_;
import com.ensemble.entreprendre.dto.CityDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.repository.ICityRepository;
import com.ensemble.entreprendre.service.ICityService;

@Service
public class CityServiceImpl implements ICityService {

	@Autowired
	ICityRepository cityRepository;

	@Autowired
	GenericConverter<City, CityDto> cityConverter;

	@Override
	public Collection<CityDto> findAll() throws ApiException {
		return this.cityConverter.entitiesToDtos(this.cityRepository.findAll(
				Sort.by(Sort.Direction.ASC, City_.NAME)), CityDto.class);
	}

}
