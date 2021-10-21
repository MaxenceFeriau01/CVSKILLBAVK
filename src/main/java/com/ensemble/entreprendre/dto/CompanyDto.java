package com.ensemble.entreprendre.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CompanyDto {

	Long id;
	String name;
	String contact;
	String siret;
	String description;
	byte[] logo;
	Set<ActivityDto> activities;
}
