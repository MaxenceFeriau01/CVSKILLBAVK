package com.ensemble.entreprendre.filter;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDtoFilter {

	Long id;
	String name;
	String contactFirstName;
	String contactLastName;
	String contactMail;
	String contactNum;
	String siret;
	String description;
}
