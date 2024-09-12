package com.ensemble.entreprendre.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobAdminDtoFilter extends JobDtoFilter {
	private String orderId = "";
	private String orderCompanyCount = "";
}
