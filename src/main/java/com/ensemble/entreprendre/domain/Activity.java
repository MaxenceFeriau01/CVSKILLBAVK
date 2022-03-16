package com.ensemble.entreprendre.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ACTIVITIES")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACTIVITY_ID")
	Long id;
	@Column(name = "ACTIVITY_NAME", nullable = false)
	String name;
	@ManyToMany(mappedBy = "activities")
	Set<Company> companies;

	@ManyToMany(mappedBy = "searchedActivities")
	Set<Company> companiesSearch;

	@ManyToMany(mappedBy = "jobs")
	Set<User> users;
}
