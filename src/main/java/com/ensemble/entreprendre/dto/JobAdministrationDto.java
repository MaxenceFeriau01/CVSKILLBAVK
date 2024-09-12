package com.ensemble.entreprendre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobAdministrationDto {

	private Long id;
	private String name;
	private Long companyCount;
	private Long userCount;
}
