package com.ensemble.entreprendre.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class CompanyDto {

	private Long id;
	private String name;
	private String contactFirstName;
	private String contactLastName;
	private String contactMail;
	private String contactNum;
	private String fixContactNum;
	private String websiteUrl;
	private String siret;
	private String description;
	private byte[] logo;
	private String town;
	private String address;
	private String postalCode;
	private String type;
	private String region;
	private String department;
	private String epci;
	private String desiredInternsNumber;
	private boolean isPaidAndLongTermInternship;
	private boolean activated;
	private Collection<ActivityDto> activities;
	private Collection<ActivityDto> searchedActivities;
	private Collection<JobDto> searchedJobs;
	private Collection<InternTypeDto> searchedInternsType;
}
