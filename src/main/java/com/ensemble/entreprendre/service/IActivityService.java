package com.ensemble.entreprendre.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.ActivityDtoFilter;

public interface IActivityService {

	Page<ActivityDto> getAllWithFilter(Pageable pageable, ActivityDtoFilter filter);

	Collection<ActivityDto> getAll();

	ActivityDto getById(long id) throws ApiException;

	ActivityDto create(ActivityDto toCreate) throws ApiException;

	ActivityDto update(Long id, ActivityDto updatedDto) throws ApiException;

	ActivityDto delete(long id) throws ApiException;
}
