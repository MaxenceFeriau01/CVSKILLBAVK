package com.ensemble.entreprendre.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Activity;
import com.ensemble.entreprendre.domain.Activity_;
import com.ensemble.entreprendre.domain.Company;
import com.ensemble.entreprendre.domain.Company_;
import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.exception.BusinessException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.filter.ActivityDtoFilter;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;
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
	public Page<ActivityDto> getAllWithFilter(Pageable pageable, ActivityDtoFilter filter) {
		Specification<Activity> specification = null;
		if (filter != null) {
			if (filter.getName() != null) {
				specification = addNameCriteria(filter, specification);
			}
		}
		if (specification == null) {
			return this.activityConverter.entitiesToDtos(this.activityRepository.findAll(pageable), ActivityDto.class);
		}
		return this.activityConverter.entitiesToDtos(this.activityRepository.findAll(specification, pageable), ActivityDto.class);
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
	
	private Specification<Activity> ensureSpecification(Specification<Activity> origin, Specification<Activity> target) {
		if (origin == null)
			return target;
		if (target == null)
			return origin;
		return Specification.where(origin).and(target);
	}
	
	private Specification<Activity> addNameCriteria(ActivityDtoFilter filter, Specification<Activity> origin) {
		Specification<Activity> target = (root, criteriaQuery, criteriaBuilder) -> {
			if (filter.getName() != null && !filter.getName().isEmpty()) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get(Activity_.NAME)), "%" + filter.getName().toLowerCase() + "%");
			} else {
				return criteriaBuilder.and();
			}
		};
		return ensureSpecification(origin, target);
	}
}
