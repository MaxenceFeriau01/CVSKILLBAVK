package com.ensemble.entreprendre.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDtoFilter {
	private String query = "";
	private String orderName = "asc";
	private String orderUserCount = "desc";
}
