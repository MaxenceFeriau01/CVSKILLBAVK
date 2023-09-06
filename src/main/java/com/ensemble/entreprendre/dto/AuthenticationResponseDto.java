package com.ensemble.entreprendre.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDto {

	private String name;

	private String firstName;

	private String token;

	private InternStatusDto internStatus;

	private Collection<ActivityDto> activities;

	private Collection<JobDto> jobs;
}
