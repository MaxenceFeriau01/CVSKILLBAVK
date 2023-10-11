package com.ensemble.entreprendre.service;

import java.util.Collection;

import com.ensemble.entreprendre.dto.JobStatDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.JobAdministrationDto;
import com.ensemble.entreprendre.dto.JobDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.JobDtoFilter;

public interface IJobService {

	Collection<JobDto> getAll();

	JobDto getById(long id) throws ApiException;

	JobDto create(JobDto toCreate) throws ApiException;

	JobDto update(Long id, JobDto updatedDto) throws ApiException;

	JobDto delete(long id) throws ApiException;

	Page<JobStatDto> getJobStats(Pageable pageable, JobDtoFilter filter);

	Page<JobAdministrationDto> getAllWithFilter(Pageable pageable, JobDtoFilter filter);
}
