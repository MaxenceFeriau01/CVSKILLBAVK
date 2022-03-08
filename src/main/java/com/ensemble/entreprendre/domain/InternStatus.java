package com.ensemble.entreprendre.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "INTERNS_STATUS")
public class InternStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STATUS_ID")
	private Long id;
	@Column(name = "STATUS_NAME", nullable = false)
	private String name;

	@OneToOne(mappedBy = "status")
	private User user;

	@OneToOne(mappedBy = "status")
	private InternType internType;
}
