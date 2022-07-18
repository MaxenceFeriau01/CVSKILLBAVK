package com.ensemble.entreprendre.dto;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

	private String email;

	private Long id;

	private String firstName;

	private String name;

	private String password;

	private String phone;

	private int postalCode;

	private LocalDate dateOfBirth;

	private LocalDate internshipStartDate;

	private LocalDate internshipEndDate;

	private InternStatusDto internStatus;

	private String civility;

	private String diploma;

	private String internshipPeriod;

	private MultipartFile cv;

	private MultipartFile coverLetter;

	private Collection<ActivityDto> activities;

	private Collection<JobDto> jobs;

	private Collection<RoleDto> roles;
}
