package com.ensemble.entreprendre.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.dto.SimpleCompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;

public interface ICompanyService {

	Page<CompanyDto> getAll(Pageable pageable, CompanyDtoFilter filter);

	Page<SimpleCompanyDto> getAllSimple(Pageable pageable, CompanyDtoFilter filter);

	CompanyDto getById(long id) throws ApiException;

	CompanyDto create(CompanyDto toCreate) throws ApiException;

	CompanyDto update(Long id, CompanyDto updatedDto) throws ApiException;

	void delete(long id) throws ApiException;

	void active(long id, boolean activated) throws ApiException;

	void apply(long id) throws ApiException, EntityNotFoundException, MessagingException, ParseException, IOException;
}
