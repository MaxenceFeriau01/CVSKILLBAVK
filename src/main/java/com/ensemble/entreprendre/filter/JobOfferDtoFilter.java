package com.ensemble.entreprendre.filter;

import java.time.LocalDate;

import lombok.Data;

@Data
public class JobOfferDtoFilter {

	Long id;
	String title;
	String description;
	Boolean active;
	LocalDate start;
}
