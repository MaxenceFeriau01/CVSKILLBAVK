package com.ensemble.entreprendre.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.JobDto;
import com.ensemble.entreprendre.exception.ApiException;

public interface IJobService {

	Collection<JobDto> getAll();

	JobDto getById(long id) throws ApiException;

	JobDto create(JobDto toCreate) throws ApiException;

	JobDto update(Long id, JobDto updatedDto) throws ApiException;

	JobDto delete(long id) throws ApiException;

	Page<JobDto> getAllWithFilter(Pageable pageable);
}
