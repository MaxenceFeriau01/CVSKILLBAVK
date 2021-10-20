package com.ensemble.entreprendre.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CompanyDto {

	Long id;
	String name;
	String contact;
	String siret;
	Set<ActivityDto> activities;
}
