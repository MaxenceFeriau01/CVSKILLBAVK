package com.ensemble.entreprendre.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "INTERNS_TYPE")
public class InternType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INTERN_STATUS_ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
	private InternStatus status;
	
	@Column(name = "INTERN_PERIOD", nullable = false)
	private String period;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;
}
