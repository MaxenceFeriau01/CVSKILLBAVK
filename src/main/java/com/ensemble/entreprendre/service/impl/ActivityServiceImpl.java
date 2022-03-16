package com.ensemble.entreprendre.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Activity;
import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.exception.BusinessException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.filter.ActivityDtoFilter;
import com.ensemble.entreprendre.projection.CustomActivity;
import com.ensemble.entreprendre.repository.IActivityRepository;
import com.ensemble.entreprendre.service.IActivityService;

@Service
public class ActivityServiceImpl implements IActivityService {

	@Autowired
	IActivityRepository activityRepository;

	@Autowired
	GenericConverter<Activity, ActivityDto> activityConverter;

	@Override
	public ActivityDto getById(long id) throws ApiException {
		return this.activityConverter.entityToDto(this.activityRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Activité n'existe pas !")), ActivityDto.class);
	}

	@Override
	public Page<CustomActivity> getAllWithFilter(Pageable pageable, ActivityDtoFilter filter) {

		if (filter != null) {
			if (filter.getName() != null) {
				return this.activityRepository.findAllWithCountsByName(pageable, filter.getName().toUpperCase());
			}
		}

		return this.activityRepository.findAllWithCountsByName(pageable, "");

	}

	@Override
	public Collection<ActivityDto> getAll() {
		return this.activityConverter.entitiesToDtos(this.activityRepository.findAll(), ActivityDto.class);
	}

	@Override
	public ActivityDto create(ActivityDto toCreate) throws ApiException {
		if (toCreate == null) {
			throw new TechnicalException("activity.post.not.null");
		}
		BusinessException businessException = new BusinessException();
		if (toCreate.getName() == null || toCreate.getName().isBlank()) {
			businessException.addMessage("activity.post.name.not.empty");
		}
		if (businessException.isNotEmpty()) {
			throw businessException;
		}
		return this.activityConverter.entityToDto(
				this.activityRepository.save(this.activityConverter.dtoToEntity(toCreate, Activity.class)),
				ActivityDto.class);
	}

	@Override
	public ActivityDto update(Long id, ActivityDto updatedDto) throws ApiException {
		this.activityRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Activité n'existe pas !"));
		return this.activityConverter.entityToDto(
				this.activityRepository.save(this.activityConverter.dtoToEntity(updatedDto, Activity.class)),
				ActivityDto.class);
	}

	@Override
	public ActivityDto delete(long id) throws ApiException {
		Activity toDelete = this.activityRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Activité n'existe pas !"));

		this.activityRepository.delete(toDelete);
		return this.activityConverter.entityToDto(toDelete, ActivityDto.class);
	}

}
