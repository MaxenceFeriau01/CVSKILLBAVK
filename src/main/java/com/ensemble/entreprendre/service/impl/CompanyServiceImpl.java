package com.ensemble.entreprendre.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Activity_;
import com.ensemble.entreprendre.domain.Company;
import com.ensemble.entreprendre.domain.Company_;
import com.ensemble.entreprendre.domain.FileDb;
import com.ensemble.entreprendre.domain.InternStatus;
import com.ensemble.entreprendre.domain.InternStatus_;
import com.ensemble.entreprendre.domain.InternType_;
import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.exception.BusinessException;
import com.ensemble.entreprendre.exception.TechnicalException;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;
import com.ensemble.entreprendre.repository.ICompanyRepository;
import com.ensemble.entreprendre.service.ICompanyService;
import com.ensemble.entreprendre.service.IMailService;
import com.ensemble.entreprendre.service.IUserService;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	ICompanyRepository companyRepository;

	@Autowired
	GenericConverter<Company, CompanyDto> companyConverter;

	@Autowired
	IMailService mailService;

	@Autowired
	IUserService userService;

	@Value("${spring.mail.replyto:reply-ee@ee.com}")
	String adminMail;

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
		if (toCreate.getContactNum() == null || toCreate.getContactNum().isBlank()) {
			businessException.addMessage("company.post.contact.not.empty");
		}
		if (businessException.isNotEmpty()) {
			throw businessException;
		}
		Company newCompany = this.companyConverter.dtoToEntity(toCreate, Company.class);
		newCompany.getSearchedInternsType().stream().forEach(t -> t.setCompany(newCompany));
		return this.companyConverter.entityToDto(this.companyRepository.save(newCompany), CompanyDto.class);
	}

	@javax.transaction.Transactional
	@Override
	public Page<CompanyDto> getAll(Pageable pageable, CompanyDtoFilter filter) {
		Specification<Company> specification = null;
		if (filter != null) {
			if (filter.getActivities() != null) {
				specification = addActivityCriteria(filter, specification);
			}
			if (filter.getStatusId() != null) {
				specification = addTypesCriteria(filter, specification);
			}
			if (filter.getIsPaidAndLongTermInternship() != null) {
				specification = addBoolCriteria(filter, specification);
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
		Company newCompany = this.companyConverter.dtoToEntity(updatedDto, Company.class);
		newCompany.getSearchedInternsType().stream().forEach(t -> t.setCompany(newCompany));
		return this.companyConverter.entityToDto(this.companyRepository.save(newCompany), CompanyDto.class);
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
			if (filter.getActivities() != null && !filter.getActivities().isEmpty()) {
				return root.join(Company_.ACTIVITIES).<Long>get(Activity_.ID).in(filter.getActivities());
			} else {
				return criteriaBuilder.and();
			}

		};
		return ensureSpecification(origin, target);
	}

	private Specification<Company> addTypesCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
		Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
			criteriaQuery.distinct(true);
			return criteriaBuilder.equal(root.join(Company_.SEARCHED_INTERNS_TYPE)
					.<InternStatus>get(InternType_.INTERN_STATUS).<Long>get(InternStatus_.ID), filter.getStatusId());
		};
		return ensureSpecification(origin, target);
	}

	private Specification<Company> addBoolCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
		Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
			criteriaQuery.distinct(true);
			return criteriaBuilder.equal(root.get(Company_.IS_PAID_AND_LONG_TERM_INTERNSHIP),
					filter.getIsPaidAndLongTermInternship());
		};
		return ensureSpecification(origin, target);
	}

	@Override
	public void apply(long id)
			throws ApiException, EntityNotFoundException, MessagingException, ParseException, IOException {
		var userDetails = userService.getConnectedUser();
		User user = userService.findByEmail(userDetails.getUsername());
		Company company = companyRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));

		// AMDIN MAIL
		HashMap<String, String> ApplyCompanyAdminTemplate = new HashMap<String, String>();
		Collection<Resource> attachments = new ArrayList<Resource>();
		Collection<File> localFiles = new ArrayList<File>();

		for (FileDb f : user.getFiles()) {

			File localFile = File.createTempFile(f.getName(), ".pdf", new File(System.getProperty("java.io.tmpdir")));

			try (OutputStream out = new FileOutputStream(localFile.getAbsolutePath())) {
				out.write(f.getData());

			}

			localFiles.add(localFile);
			attachments.add(new FileSystemResource(localFile.getAbsolutePath()));
		}

		Optional<Collection<Resource>> opAttachments = Optional.of(attachments);

		ApplyCompanyAdminTemplate.put("firstName", user.getFirstName());
		ApplyCompanyAdminTemplate.put("lastName", user.getName());
		ApplyCompanyAdminTemplate.put("email", user.getEmail());
		ApplyCompanyAdminTemplate.put("phoneNumber", user.getPhone());
		ApplyCompanyAdminTemplate.put("companyName", company.getName());

		this.mailService.prepareMail(MailSubject.ApplyCompanyAdminTemplate, "Une demande de stage a été effectuée",
				adminMail, ApplyCompanyAdminTemplate, opAttachments);

		localFiles.stream().forEach(f -> f.delete());
		// END ADMIN MAIL

		// USER MAIL
		HashMap<String, String> applyConfirmParams = new HashMap<String, String>();

		applyConfirmParams.put("firstName", user.getFirstName());
		applyConfirmParams.put("lastName", user.getName());
		applyConfirmParams.put("companyName", company.getName());

		this.mailService.prepareMail(MailSubject.ApplyConfirm, "Confirmation de demande de stage", user.getEmail(),
				applyConfirmParams, null);

		// END USER MAIL

	}

}
