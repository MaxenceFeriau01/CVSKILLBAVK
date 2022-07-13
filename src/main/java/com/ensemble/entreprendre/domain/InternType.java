package com.ensemble.entreprendre.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import lombok.Getter;
import lombok.Setter;

@TypeDefs({
		@TypeDef(name = "string-array", typeClass = StringArrayType.class)
})
@Entity
@Getter
@Setter
@Table(name = "INTERNS_TYPE")

public class InternType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INTERN_TYPE_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
	private InternStatus internStatus;

	@Type(type = "string-array")
	@Column(name = "periods", columnDefinition = "text[]")
	private String[] periods;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;
}
