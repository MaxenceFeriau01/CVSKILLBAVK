package com.ensemble.entreprendre.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "COMPANY")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPANY_ID")
	Long id;
	@Column(name = "COMPANY_NAME", nullable = false)
	String name;
	@Column(name = "COMPANY_CONTACT", nullable = false)
	String contact;
//	@Column(name = "SIRET", unique=true, length = 14)
//    @Size(min = 14, max = 14)
//	String siret;
}
