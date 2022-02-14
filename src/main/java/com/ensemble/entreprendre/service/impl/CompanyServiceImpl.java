package com.ensemble.entreprendre.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import com.ensemble.entreprendre.domain.JobOffer;
import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.exception.BusinessException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;
import com.ensemble.entreprendre.repository.ICompanyRepository;
import com.ensemble.entreprendre.service.ICompanyService;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	ICompanyRepository companyRepository;

	@Autowired
	GenericConverter<Company, CompanyDto> companyConverter;

	@Override
	public CompanyDto getById(long id) throws ApiException {
		return this.companyConverter.entityToDto(this.companyRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !")), CompanyDto.class);
	}

	@Override
	public CompanyDto create(CompanyDto toCreate) throws ApiException {
		if (toCreate == null) {
			throw new TechnicalException("company.post.not.null");
		}
		BusinessException businessException = new BusinessException();
		if (toCreate.getContactFirstName() == null || toCreate.getContactFirstName().isBlank()) {
			businessException.addMessage("company.post.contact.not.empty");
		}
		if (toCreate.getContactLastName() == null || toCreate.getContactLastName().isBlank()) {
			businessException.addMessage("company.post.contact.not.empty");
		}
		if (toCreate.getContactMail() == null || toCreate.getContactMail().isBlank()) {
			businessException.addMessage("company.post.contact.not.empty");
		}
		if (toCreate.getContactNum() == null || toCreate.getContactNum().isBlank()) {
			businessException.addMessage("company.post.contact.not.empty");
		}
		if (businessException.isNotEmpty()) {
			throw businessException;
		}

		return this.companyConverter.entityToDto(
				this.companyRepository.save(this.companyConverter.dtoToEntity(toCreate, Company.class)),
				CompanyDto.class);
	}

	@Override
	public Page<CompanyDto> getAll(Pageable pageable, CompanyDtoFilter filter) {
		Specification<Company> specification = null;
		if (filter != null) {
			if (filter.getActivityId() != null) {
				specification = addActivityCriteria(filter, specification);
			}
		}
		if (specification == null) {
			return this.companyConverter.entitiesToDtos(this.companyRepository.findAll(pageable), CompanyDto.class);
		}
		return this.companyConverter.entitiesToDtos(this.companyRepository.findAll(specification, pageable),
				CompanyDto.class);
	}

	@Override
	public CompanyDto update(Long id, CompanyDto updatedDto) throws ApiException {
		Company oldCompany = this.companyRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));
		updatedDto.setId(id);
		if (updatedDto.getLogo() == null) {
			// Set the old logo if not changed
			updatedDto.setLogo(oldCompany.getLogo());
		}

		return this.companyConverter.entityToDto(
				this.companyRepository.save(this.companyConverter.dtoToEntity(updatedDto, Company.class)),
				CompanyDto.class);
	}

	@Override
	public CompanyDto delete(long id) throws ApiException {
		Company toDelete = this.companyRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));
		this.companyRepository.delete(toDelete);
		return this.companyConverter.entityToDto(toDelete, CompanyDto.class);
	}

	private Specification<Company> ensureSpecification(Specification<Company> origin, Specification<Company> target) {
		if (origin == null)
			return target;
		if (target == null)
			return origin;
		return Specification.where(origin).and(target);
	}

	private Specification<Company> addActivityCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
		Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
			criteriaQuery.distinct(true);
			return criteriaBuilder.equal(root.join(Company_.ACTIVITIES).<Long>get(Activity_.ID),
					filter.getActivityId());
		};
		return ensureSpecification(origin, target);
	}

}
