package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Company;
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
		if (toCreate.getContactNum()== null || toCreate.getContactNum().isBlank()) {
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
			if (filter.getId() != null) {
				specification = addIdCriteria(filter, specification);
			}
			if (filter.getName() != null) {
				specification = addNameCriteria(filter, specification);
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
		this.companyRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));
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

	private Specification<Company> addIdCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
		Specification<Company> target = (company, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.equal(company.get("id"), filter.getId());
		return ensureSpecification(origin, target);
	}

	private Specification<Company> addNameCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
		Specification<Company> target = (company, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.upper(company.get("name")), "%" + filter.getName().toUpperCase() + "%");
		return ensureSpecification(origin, target);
	}

//	private Specification<Company> addContactCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
//		Specification<Company> target = (company, criteriaQuery, criteriaBuilder) -> criteriaBuilder
//				.greaterThanOrEqualTo(company.get("contact"), filter.getContact());
//		return ensureSpecification(origin, target);
//	}

}
