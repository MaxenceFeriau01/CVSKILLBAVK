package com.ensemble.entreprendre.dto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

	private String email;

	private String firstName;

	private String name;

	private String password;

	private String phone;

	private int postalCode;

	private LocalDate dateOfBirth;

	private String status;

	private String civility;

	private String diploma;

	private String internshipPeriod;

	private MultipartFile cv;

	private MultipartFile coverLetter;

	private Set<ActivityDto> activities;

	private Set<JobDto> jobs;
}
