package com.ensemble.entreprendre.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "JOBS")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "JOB_ID")
	Long id;
	@Column(nullable = false)
	String name;

	@ManyToMany(mappedBy = "searchedJobs")
	Set<Company> companies;

	@ManyToMany(mappedBy = "jobs")
	Set<User> users;
}
