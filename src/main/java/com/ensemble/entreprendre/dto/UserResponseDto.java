package com.ensemble.entreprendre.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

	private Long id;

	private String email;

	private String firstName;

	private String name;

	private String phone;

	private int postalCode;

	private LocalDate dateOfBirth;

	private LocalDate internshipStartDate;

	private LocalDate internshipEndDate;

	private boolean activated;

	private String civility;

	private String diploma;

	private String internshipPeriod;

	private String searchSubject;

	private InternStatusDto internStatus;

	private Collection<PartialFileDbDto> files;

	private Collection<JobDto> jobs;

	private Collection<RoleDto> roles;

	private Collection<CompanySimpleDto> appliedCompanies;

	private Long profileUpdateCount;

	private LocalDateTime createdDate;

	private LocalDateTime lastModifiedDate;
}
