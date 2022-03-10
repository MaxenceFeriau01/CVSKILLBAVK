package com.ensemble.entreprendre.filter;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDtoFilter {
	Collection<Long> activities;
	Long statusId;
	Boolean isPaidAndLongTermInternship;
}
