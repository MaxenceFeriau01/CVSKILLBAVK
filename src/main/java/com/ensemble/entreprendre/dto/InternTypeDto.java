package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternTypeDto {

	private Long id;
	
	private InternStatusDto internStatus;
	
	private String period;

}
