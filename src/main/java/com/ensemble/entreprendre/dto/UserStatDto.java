package com.ensemble.entreprendre.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatDto {
    
    private Long id;

    private LocalDateTime createdDate;

    private String civility;
    
    private String name;
	
	private String firstName;

    private String phone;

	private String email;

	private LocalDate dateOfBirth;

	private int postalCode;

	private InternStatusDto internStatus;

    private String internshipPeriod;

    private String diploma;

	private Integer updateProfil;
}
