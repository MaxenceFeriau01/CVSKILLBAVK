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
import javax.persistence.criteria.Path;

import com.ensemble.entreprendre.domain.*;
import com.ensemble.entreprendre.util.IStringUtil;
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
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.dto.SimpleCompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;
import com.ensemble.entreprendre.repository.ICompanyRepository;
import com.ensemble.entreprendre.repository.IUserApplyCompanyRepository;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.service.IConnectedUserService;
import com.ensemble.entreprendre.service.ICompanyService;
import com.ensemble.entreprendre.service.IMailService;
import com.ensemble.entreprendre.service.IUserService;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    ICompanyRepository companyRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserApplyCompanyRepository userApplyCompanyRepository;

    @Autowired
    GenericConverter<Company, CompanyDto> companyConverter;

    @Autowired
    GenericConverter<Company, SimpleCompanyDto> simpleCompanyConverter;

    @Autowired
    IMailService mailService;

    @Autowired
    IUserService userService;

    @Autowired
    IConnectedUserService connectedUserService;

    @Value("${spring.mail.replyto:reply-ee@ee.com}")
    String adminMail;

    @Value("${period.internship.paid}")
    Long periodWhenInternshipIsPaid;

    @Autowired
    private IStringUtil stringUtil;

    @Override
    public CompanyDto getById(long id) throws ApiException {
        return this.companyConverter.entityToDto(this.companyRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !")), CompanyDto.class);
    }

    @Override
    public CompanyDto create(CompanyDto toCreate) throws ApiException {
        Company newCompany = this.companyConverter.dtoToEntity(toCreate, Company.class);
        newCompany.getSearchedInternsType().stream().forEach(t -> t.setCompany(newCompany));
        return this.companyConverter.entityToDto(this.companyRepository.save(newCompany), CompanyDto.class);
    }

    @Override
    public Page<CompanyDto> getAll(Pageable pageable, CompanyDtoFilter filter) throws ApiException {
        Specification<Company> specification = null;
        filter.setActivated(true);

        specification = checkIfUserWantPaidInternship(specification);

        specification = addCriterias(filter, specification);

        if (specification == null) {
            return this.companyConverter.entitiesToDtos(this.companyRepository.findAll(pageable), CompanyDto.class);
        }

        return this.companyConverter.entitiesToDtos(this.companyRepository.findAll(specification, pageable),
                CompanyDto.class);
    }

    @Override
    public Page<SimpleCompanyDto> getAllSimple(Pageable pageable, CompanyDtoFilter filter) {
        Specification<Company> specification = null;
        specification = addCriterias(filter, specification);

        if (specification == null) {
            return this.simpleCompanyConverter.entitiesToDtos(this.companyRepository.findAll(pageable),
                    SimpleCompanyDto.class);
        }

        return this.simpleCompanyConverter.entitiesToDtos(this.companyRepository.findAll(specification, pageable),
                SimpleCompanyDto.class);
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
        newCompany.setActivated(oldCompany.isActivated());
        newCompany.getSearchedInternsType().stream().forEach(t -> t.setCompany(newCompany));
        return this.companyConverter.entityToDto(this.companyRepository.save(newCompany), CompanyDto.class);
    }

    @Override
    public void delete(long id) throws ApiException {
        Company toDelete = this.companyRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));
        this.companyRepository.delete(toDelete);

    }

    @Override
    public void active(long id, boolean activated) throws ApiException {
        Company company = this.companyRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));
        company.setActivated(activated);
        this.companyRepository.save(company);

    }

    @Override
    public void apply(long id)
            throws ApiException, EntityNotFoundException, MessagingException, ParseException, IOException {
        var userDetails = connectedUserService.getConnectedUser();
        User user = userService.findByEmail(userDetails.getUsername());
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cette Entreprise n'existe pas !"));
        Boolean isAlreadyApplied = user.getAppliedCompanies().stream().anyMatch((p) -> {
            return p.getCompany().getId() == company.getId();
        });

        if (isAlreadyApplied) {
            throw new ApiException("Vous avez déjà postulé à cette offre de stage.");
        }

        // Save the apply
        UserApplyCompany applyCompany = new UserApplyCompany();
        applyCompany.setCompany(company);
        applyCompany.setUser(user);
        userApplyCompanyRepository.save(applyCompany);

        // ADMIN MAIL
        HashMap<String, Object> ApplyCompanyAdminTemplate = new HashMap<String, Object>();
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
        HashMap<String, Object> applyCompanyParams = new HashMap<String, Object>();

        applyCompanyParams.put("firstName", user.getFirstName());
        applyCompanyParams.put("lastName", user.getName());
        applyCompanyParams.put("companyName", company.getName());
        applyCompanyParams.put("companyContactMail",
                company.getContactMail() != null ? company.getContactMail() : "");
        applyCompanyParams.put("companyContactName",
                company.getContactFirstName() + " " + company.getContactLastName());
        applyCompanyParams.put("companyContactPhone",
                company.getFixContactNum() != null ? company.getFixContactNum() : "");
        applyCompanyParams.put("companyContactMobile",
                company.getContactNum() != null ? company.getContactNum() : "");
        applyCompanyParams.put("companyFullAddress",
                company.getAddress() + ", " + company.getCity().getPostalCode() + " "
                        + company.getCity().getName());

        this.mailService.prepareMail(MailSubject.ApplyCompany, "Comment faire ma demande de stage", user.getEmail(),
                applyCompanyParams, null);

        // END USER MAIL
    }

    private Specification<Company> addCriterias(CompanyDtoFilter filter, Specification<Company> specification) {
        if (filter != null) {
            if (filter.getActivities() != null) {
                specification = addActivityCriteria(filter, specification);
            }

            if (filter.getJobs() != null) {
                specification = addJobsCriteria(filter, specification);
            }

            if (filter.getStatusId() != null) {
                specification = addTypesCriteria(filter, specification);
            }

            if (filter.getName() != null) {
                specification = addNameCriteria(filter, specification);
            }

            if (filter.getActivated() != null) {
                specification = addActivatedCriteria(filter, specification);
            }
            if (Boolean.TRUE.equals(stringUtil.isValueSet(filter.getSortField())) && filter.getSortType() != null) {
                specification = addSortingCriteria(filter, specification);
            }
        }
        return specification;
    }
    private Specification<Company> ensureSpecification(Specification<Company> origin, Specification<Company> target) {
        if (origin == null)
            return target;
        if (target == null)
            return origin;
        return Specification.where(origin).and(target);
    }

    private Specification<Company> addSortingCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
        Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> sortField = null;

            switch (filter.getSortField()) {
                case "activated":
                    sortField = root.get(Company_.ACTIVATED);
                    break;
                default:
                case "name":
                    sortField = root.get(Company_.NAME);
                    break;
                case "city":
                    sortField = root.join(Company_.CITY).get(City_.NAME);
                    break;
                case "type":
                    sortField = root.get(Company_.TYPE);
                    break;
            }

            if (filter.getSortType().equalsIgnoreCase("asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(sortField));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(sortField));
            }

            return criteriaBuilder.and();
        };
        return ensureSpecification(origin, target);
    }

    private Specification<Company> addActivityCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
        Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
            if (filter.getActivities() != null && !filter.getActivities().isEmpty()) {
                return root.join(Company_.SEARCHED_ACTIVITIES).<Long>get(Activity_.ID).in(filter.getActivities());
            } else {
                return criteriaBuilder.and();
            }

        };
        return ensureSpecification(origin, target);
    }

    private Specification<Company> addJobsCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
        Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
            if (filter.getJobs() != null && !filter.getJobs().isEmpty()) {
                return root.join(Company_.SEARCHED_JOBS).<Long>get(Job_.ID).in(filter.getJobs());
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

    private Specification<Company> addActivatedCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
        Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            return criteriaBuilder.equal(root.get(Company_.ACTIVATED), filter.getActivated());
        };
        return ensureSpecification(origin, target);
    }

    private Specification<Company> addNameCriteria(CompanyDtoFilter filter, Specification<Company> origin) {
        Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.upper(root.get(Company_.NAME)),
                        "%" + filter.getName().toUpperCase() + "%");
            } else {
                return criteriaBuilder.and();
            }
        };
        return ensureSpecification(origin, target);
    }

    private Specification<Company> addIsPaidInternshipCriteria(Specification<Company> origin) {
        Specification<Company> target = (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            return criteriaBuilder.equal(root.get(Company_.IS_PAID_AND_LONG_TERM_INTERNSHIP),
                    true);
        };
        return ensureSpecification(origin, target);
    }

    private Specification<Company> checkIfUserWantPaidInternship(Specification<Company> specification)
            throws ApiException, ApiNotFoundException {
        if (connectedUserService.isUserConnected() && !connectedUserService.isAdmin()) {
            User user = userService.findByEmail(connectedUserService.getConnectedUser().getUsername());
            if (user.getDesiredInternshipPeriodDays() > periodWhenInternshipIsPaid) {
                specification = this.addIsPaidInternshipCriteria(specification);
            }
        }
        return specification;
    }
}
