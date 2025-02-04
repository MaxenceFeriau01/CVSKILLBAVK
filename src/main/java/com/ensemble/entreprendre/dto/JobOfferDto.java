package com.ensemble.entreprendre.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class JobOfferDto {

	Long id;
	String title;
	String description;
	Boolean active;
	LocalDate start;
}
