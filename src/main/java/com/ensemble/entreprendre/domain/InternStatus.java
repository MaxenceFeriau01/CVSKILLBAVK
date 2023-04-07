package com.ensemble.entreprendre.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INTERNS_STATUS")
public class InternStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STATUS_ID")
	private Long id;
	@Column(name = "STATUS_NAME", nullable = false)
	private String name;

	@OneToMany(mappedBy = "internStatus", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<User> user;

	@OneToMany(mappedBy = "internStatus", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<InternType> internType;
}
