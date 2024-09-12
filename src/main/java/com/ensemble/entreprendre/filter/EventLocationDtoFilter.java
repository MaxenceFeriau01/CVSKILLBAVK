package com.ensemble.entreprendre.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventLocationDtoFilter {
    private String query;
    private String sortField;
	private String sortType;
}
