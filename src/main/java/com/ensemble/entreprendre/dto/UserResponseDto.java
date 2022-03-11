package com.ensemble.entreprendre.dto;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_ID")
	private Long id;

	private String email;

	private String firstName;

	private String name;

	private String phone;

	private int postalCode;

	private LocalDate dateOfBirth;

	private String civility;

	private String diploma;

	private String internshipPeriod;
	
	private InternStatusDto internStatus;

	Collection<PartialFileDbDto> files;

	Collection<ActivityDto> activities;

	Collection<JobDto> jobs;

	private Collection<RoleDto> roles;

}
