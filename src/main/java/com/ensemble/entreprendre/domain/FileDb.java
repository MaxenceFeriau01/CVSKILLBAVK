package com.ensemble.entreprendre.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.ensemble.entreprendre.domain.enumeration.FileTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "FILES")
@NoArgsConstructor
@AllArgsConstructor

public class FileDb {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private String name;

	@Column()
	@Enumerated(EnumType.STRING)
	private FileTypeEnum type;

	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] data;

	@ManyToOne
	@JoinColumn(name = "USR_ID", nullable = false)
	private User user;

}
