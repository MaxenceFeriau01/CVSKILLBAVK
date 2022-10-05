package com.ensemble.entreprendre.filter;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDtoFilter {
	Collection<Long> activities;
	Collection<Long> jobs;
	Long statusId;
	String name;
	Boolean activated;
}
