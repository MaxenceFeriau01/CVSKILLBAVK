package com.ensemble.entreprendre.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "COMPANIES")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPANY_ID")
	private Long id;

	@Column(name = "COMPANY_NAME", nullable = false)
	private String name;

	@Column(name = "COMPANY_CONTACT_FIRSTNAME", nullable = false)
	private String contactFirstName;

	@Column(name = "COMPANY_CONTACT_LASTNAME", nullable = false)
	private String contactLastName;

	@Column(name = "COMPANY_CONTACT_MAIL", nullable = true)
	private String contactMail;

	@Column(name = "COMPANY_CONTACT_NUM", nullable = false)
	private String contactNum;

	@Column(name = "COMPANY_SIRET", length = 14, nullable = false)
	@Pattern(regexp = "[0-9]{14}", message = "{company.invalid.siret}")
	private String siret;

	@Column(name = "COMPANY_TOWN", nullable = false)
	private String town;

	@Column(name = "COMPANY_address", nullable = true)
	private String address;

	@Column(name = "COMPANY_POSTAL_CODE", nullable = false)
	private String postalCode;

	@Column(name = "COMPANY_TYPE", nullable = false)
	private String type;

	@Column(name = "COMPANY_DESCRIPTION")
	private String description;

	@Column(name = "COMPANY_DESIRED_INTERNS_NUMBER", nullable = false)
	private String desiredInternsNumber;

	@Column(name = "COMPANY_ACCEPTS_LONG_PAID_INTERNSHIP", nullable = false)
	boolean isPaidAndLongTermInternship;

	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "COMPANY_LOGO")
	private byte[] logo;

	@ManyToMany
	@JoinTable(name = "companies_activities", joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ACTIVITY_ID"))
	private Set<Activity> activities;

	@ManyToMany
	@JoinTable(name = "companies_searched_jobs", joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID"))
	private Set<Job> searchedJobs;

	@ManyToMany
	@JoinTable(name = "companies_searched_activities", joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ACTIVITY_ID"))
	private Set<Activity> searchedActivities;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<InternType> searchedInternType;

}
