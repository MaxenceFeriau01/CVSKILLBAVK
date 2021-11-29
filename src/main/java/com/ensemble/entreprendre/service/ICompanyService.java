package com.ensemble.entreprendre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;

public interface ICompanyService {

	Page<CompanyDto> getAll(Pageable pageable, CompanyDtoFilter filter);

	CompanyDto getById(long id) throws ApiException;

	CompanyDto create(CompanyDto toCreate) throws ApiException;
	
	CompanyDto update (CompanyDto updatedDto) throws ApiException;
	
	CompanyDto delete (long id) throws ApiException;
}
