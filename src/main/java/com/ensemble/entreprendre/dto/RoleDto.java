package com.ensemble.entreprendre.dto;

import com.ensemble.entreprendre.domain.enumeration.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RoleDto {

	private long id;
	private RoleEnum role;
}
