package com.ensemble.entreprendre.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class CompanyDto {

	Long id;
	String name;
	String contactFirstName;
	String contactLastName;
	String contactMail;
	String contactNum;
	String siret;
	String description;
	byte[] logo;
	String town;
	String address;
	String postalCode;
	String type;
	String desiredInternsNumber;
	boolean isPaidAndLongTermInternship;
	Collection<ActivityDto> activities;
	Collection<ActivityDto> searchedActivities;
	Collection<JobDto> searchedJobs;
	Collection<InternTypeDto> searchedInternsType;
}
