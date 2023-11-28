package com.ensemble.entreprendre.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoFilter {
	private String name;
	private boolean activated;
	private String sortField;
	private String sortType;
}
