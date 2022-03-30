package com.ensemble.entreprendre.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomActivity  {

	private Long id;
	private String name;
	private Long companyCount;
	private Long companySearchCount;
	private Long userCount;
}
