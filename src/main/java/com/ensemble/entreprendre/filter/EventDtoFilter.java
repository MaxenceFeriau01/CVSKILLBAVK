package com.ensemble.entreprendre.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDtoFilter {
    private String query;
    private Boolean active;
    private Boolean onlyCurrentEvents = false;
    private String type;
	private String sortField;
	private String sortType;
}
