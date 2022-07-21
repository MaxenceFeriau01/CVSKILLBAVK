package com.ensemble.entreprendre.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class SimpleCompanyDto {
	Long id;
	String name;
	String siret;
	CityDto city;
	String postalCode;
	Collection<ActivityDto> searchedActivities;
	Boolean activated;
}
