package com.ensemble.entreprendre.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoFilter {
	private Integer postalCode;
	private Boolean activated;
	private String query;
	private String sortField;
	private String sortType;
	private Boolean export = false;
}
