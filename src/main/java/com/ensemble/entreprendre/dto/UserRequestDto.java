package com.ensemble.entreprendre.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String name;

	@NotEmpty
	private String password;

	private String phone;

	private int postalCode;

	private LocalDate dateOfBirth;

	@NotEmpty
	private String status;

	@NotEmpty
	private String civility;

	private String diploma;

	private String internshipPeriod;

	private Set<ActivityDto> activities;

	private Set<JobDto> jobs;
}
