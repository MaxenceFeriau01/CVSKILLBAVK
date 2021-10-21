package com.ensemble.entreprendre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.exception.ApiException;

public interface IActivityService {

	Page<ActivityDto> getAll(Pageable pageable);

	ActivityDto getById(long id) throws ApiException;

	ActivityDto create(ActivityDto toCreate) throws ApiException;
	
	ActivityDto update (ActivityDto updatedDto) throws ApiException;
	
	ActivityDto delete (long id) throws ApiException;
}
