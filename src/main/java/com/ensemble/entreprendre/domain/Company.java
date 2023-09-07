package com.ensemble.entreprendre.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Data
@Table(name = "COMPANIES")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPANY_ID")
	private Long id;

	@Column(name = "COMPANY_NAME", nullable = false)
	private String name;

	@Column(name = "COMPANY_CONTACT_FIRSTNAME", nullable = true)
	private String contactFirstName;

	@Column(name = "COMPANY_CONTACT_LASTNAME", nullable = true)
	private String contactLastName;

	@Column(name = "COMPANY_CONTACT_MAIL", nullable = true)
	private String contactMail;

	// mobile
	@Column(name = "COMPANY_CONTACT_NUM", nullable = true)
	private String contactNum;

	@Column(name = "COMPANY_FIX_CONTACT_NUM", nullable = true)
	private String fixContactNum;

	@Column(name = "COMPANY_WEBSITE", nullable = true)
	@Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
	private String websiteUrl;

	@Column(name = "COMPANY_SIRET", length = 14, nullable = true)
	@Pattern(regexp = "[0-9]{14}", message = "{company.invalid.siret}")
	private String siret;

	@Column(name = "COMPANY_address", nullable = true)
	private String address;

	@Column(name = "COMPANY_TYPE", nullable = false)
	private String type;

	@Column(name = "COMPANY_REGION", nullable = true)
	private String region;

	@Column(name = "COMPANY_DEPARTMENT", nullable = true)
	private String department;

	@Column(name = "COMPANY_EPCI", nullable = true)
	private String epci;

	@Column(name = "COMPANY_DESCRIPTION", length = 1024)
	private String description;

	@Column(name = "COMPANY_DESIRED_INTERNS_NUMBER", nullable = false)
	private String desiredInternsNumber;

	@Column(name = "COMPANY_ACCEPTS_LONG_PAID_INTERNSHIP", nullable = false, columnDefinition = "boolean default false")
	boolean isPaidAndLongTermInternship;

	@Column(name = "COMPANY_MINOR_ACCEPTED", nullable = false, columnDefinition = "boolean default false")
	boolean minorAccepted;

	@OneToMany(mappedBy = "company")
	private Collection<UserApplyCompany> users;

	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "COMPANY_LOGO")
	private byte[] logo;

	@Column(name = "COMPANY_ACTIVATED", nullable = false, columnDefinition = "boolean default true")
	private boolean activated = true;

	@ManyToMany
	@JoinTable(name = "companies_activities", joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ACTIVITY_ID"))
	private Collection<Activity> activities;

	@ManyToMany
	@JoinTable(name = "companies_searched_jobs", joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID"))
	private Collection<Job> searchedJobs;

	@ManyToMany
	@JoinTable(name = "companies_searched_activities", joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ACTIVITY_ID"))
	private Collection<Activity> searchedActivities;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<InternType> searchedInternsType;

	@ManyToOne
	@JoinColumn(name = "CITY_ID")
	private City city;

}
