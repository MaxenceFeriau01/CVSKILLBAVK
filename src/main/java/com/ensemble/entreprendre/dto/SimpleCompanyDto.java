package com.ensemble.entreprendre.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class SimpleCompanyDto {
	Long id;
	String name;
	String type;
	CityDto city;
	Collection<ActivityDto> searchedActivities;
	Boolean activated;
}
