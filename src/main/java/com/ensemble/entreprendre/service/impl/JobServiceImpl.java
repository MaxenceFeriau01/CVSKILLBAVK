package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Job;
import com.ensemble.entreprendre.dto.JobDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.exception.BusinessException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.repository.IJobRepository;
import com.ensemble.entreprendre.service.IJobService;

@Service
public class JobServiceImpl implements IJobService {

	@Autowired
	IJobRepository jobRepository;

	@Autowired
	GenericConverter<Job, JobDto> jobConverter;

	@Override
	public JobDto getById(long id) throws ApiException {
		return this.jobConverter.entityToDto(this.jobRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Activité n'existe pas !")), JobDto.class);
	}

	@Override
	public Page<JobDto> getAll(Pageable pageable) {
		return this.jobConverter.entitiesToDtos(this.jobRepository.findAll(pageable), JobDto.class);
	}

	@Override
	public JobDto create(JobDto toCreate) throws ApiException {
		if (toCreate == null) {
			throw new TechnicalException("job.post.not.null");
		}
		BusinessException businessException = new BusinessException();
		if (toCreate.getName()== null || toCreate.getName().isBlank()) {
			businessException.addMessage("job.post.name.not.empty");
		}
		if (businessException.isNotEmpty()) {
			throw businessException;
		}
		return this.jobConverter.entityToDto(
				this.jobRepository.save(this.jobConverter.dtoToEntity(toCreate, Job.class)),
				JobDto.class);
	}

	@Override
	public JobDto update(Long id, JobDto updatedDto) throws ApiException {
		this.jobRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Activité n'existe pas !"));
		return this.jobConverter.entityToDto(
				this.jobRepository.save(this.jobConverter.dtoToEntity(updatedDto, Job.class)),
				JobDto.class);
	}

	@Override
	public JobDto delete(long id) throws ApiException {
		Job toDelete = this.jobRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Activité n'existe pas !"));
		this.jobRepository.delete(toDelete);
		return this.jobConverter.entityToDto(toDelete, JobDto.class);
	}
}
