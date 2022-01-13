package com.ensemble.entreprendre.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	Set<ActivityDto> activities;
}
