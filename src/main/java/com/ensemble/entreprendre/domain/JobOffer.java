package com.ensemble.entreprendre.domain;

import java.time.LocalDate;

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
@Table(name = "JOB_OFFER")
public class JobOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "JBO_ID")
	Long id;
	@Column(name = "JBO_TITLE")
	String title;
	@Column(name = "JBO_DESCRIPTION")
	String description;
	@Column(name = "JBO_ACTIVE",nullable = false,columnDefinition = "boolean default true")
	boolean active = true;
	@Column(name = "JBO_START")
	LocalDate start;
	
}
