package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.JobOffer;
import com.ensemble.entreprendre.domain.JobOffer_;
import com.ensemble.entreprendre.dto.JobOfferDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.exception.BusinessException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.filter.JobOfferDtoFilter;
import com.ensemble.entreprendre.repository.IJobOfferRepository;
import com.ensemble.entreprendre.service.IJobOfferService;

@Service
public class JobOfferServiceImpl implements IJobOfferService {

	@Autowired
	IJobOfferRepository jobOfferRepo;
	
	@Autowired
	GenericConverter<JobOffer, JobOfferDto> jobOfferConverter;
	
	@Override
	public Page<JobOfferDto> getAll(Pageable pageable, JobOfferDtoFilter filter) {
		Specification<JobOffer> specification = null;
		if(filter!=null) {
			if (filter.getActive() != null) {
				specification = addActiveCriteria(filter,specification);
			}
		}
		if(specification == null) {
			return this.jobOfferConverter.entitiesToDtos(this.jobOfferRepo.findAll(pageable),JobOfferDto.class);
		} else {
			return this.jobOfferConverter.entitiesToDtos(this.jobOfferRepo.findAll(specification, pageable),JobOfferDto.class);
		}
	}


	private Specification<JobOffer> ensureSpecification(Specification<JobOffer> origin,	Specification<JobOffer> target) {
		if(origin == null)
			return target;
		if(target == null)
			return origin;
		return Specification.where(origin).and(target);
	}


	private Specification<JobOffer> addActiveCriteria(JobOfferDtoFilter filter, Specification<JobOffer> origin) {
		Specification<JobOffer> target = (jobOffer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(jobOffer.get(JobOffer_.ACTIVE), filter.getActive()); 
		return ensureSpecification(origin, target);
	}

	@Override
	public JobOfferDto getById(long id) throws ApiException {
		return this.jobOfferConverter.entityToDto(this.jobOfferRepo.findById(id).orElseThrow(()-> new ApiNotFoundException("Cette Offre n'existe pas !")),JobOfferDto.class);
	}


	@Override
	public JobOfferDto create(JobOfferDto toCreate) throws ApiException {
		if (toCreate == null) {
			throw new TechnicalException("joboffer.post.not.null");
		}
		BusinessException businessException = new BusinessException();
		if(toCreate.getTitle() == null || toCreate.getTitle().isBlank()) {
			businessException.addMessage("joboffer.post.title.not.empty");
		}
		if (toCreate.getDescription() == null || toCreate.getDescription().isBlank()) {
			businessException.addMessage("joboffer.post.description.not.empty");
		}
		if(businessException.isNotEmpty()) {
			throw businessException;
		}
		if(toCreate.getActive() == null) {
			toCreate.setActive(true);
		}
		return this.jobOfferConverter.entityToDto(this.jobOfferRepo.save(this.jobOfferConverter.dtoToEntity(toCreate, JobOffer.class)), JobOfferDto.class);
	}

}
