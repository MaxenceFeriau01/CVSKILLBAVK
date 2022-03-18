package com.ensemble.entreprendre.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ensemble.entreprendre.domain.technical.FullAuditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User extends FullAuditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_ID")
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = true)
	private int postalCode;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@ManyToOne
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
	private InternStatus internStatus;

	@Column(nullable = false)
	private String civility;

	@Column(nullable = true)
	private String diploma;

	@Column(nullable = true)
	private String internshipPeriod;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean activated = true;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<FileDb> files;

	@ManyToMany
	@JoinTable(name = "users_roles", inverseJoinColumns = @JoinColumn(name = "ROL_ID", foreignKey = @ForeignKey(name = "FK_USR_ROL_ROLE"), referencedColumnName = "ROL_ID", nullable = false, updatable = false), joinColumns = @JoinColumn(name = "USR_ID", foreignKey = @ForeignKey(name = "FK_USR_ROL_USER"), referencedColumnName = "USR_ID", nullable = false, updatable = false))
	private Collection<Role> roles;

	@ManyToMany
	@JoinTable(name = "users_jobs", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID"), inverseJoinColumns = @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID"))
	private Collection<Job> jobs;

	@ManyToMany
	@JoinTable(name = "users_activities", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID"), inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ACTIVITY_ID"))
	Collection<Activity> activities;
}
