package com.ensemble.entreprendre.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

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
	
	@Column(name = "COMPANY_SIRET", unique = true, length = 14)
	@Pattern(regexp="[0-9]{14}", message="{company.invalid.siret}")
	String siret;
	
	@Column(name = "COMPANY_DESCRIPTION")
	String description;
	
	@Lob
    @Column(name = "COMPANY_LOGO", columnDefinition="BLOB")
    private byte[] logo;
	
	@ManyToMany
	@JoinTable(name = "companies_activities",
		joinColumns=@JoinColumn(name="COMPANY_ID", referencedColumnName="COMPANY_ID"),
    	inverseJoinColumns=@JoinColumn(name="ACTIVITY_ID", referencedColumnName="ACTIVITY_ID"))
    Set<Activity> activities;
}
