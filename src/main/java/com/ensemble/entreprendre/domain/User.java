package com.ensemble.entreprendre.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

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
import javax.persistence.Table;

import com.ensemble.entreprendre.domain.enumeration.FileTypeEnum;
import com.ensemble.entreprendre.domain.technical.FullAuditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	@Column(nullable = true)
	private LocalDate internshipStartDate;

	@Column(nullable = true)
	private LocalDate internshipEndDate;

	@Column(nullable = true)
	private String resetPasswordToken;

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
	private Boolean activated = true;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<FileDb> files;

	@OneToMany(mappedBy = "user")
	private Collection<UserApplyCompany> appliedCompanies;

	@OneToMany(mappedBy = "user", orphanRemoval = false)
	private Collection<Visit> visits;

	@ManyToMany
	@JoinTable(name = "users_roles", inverseJoinColumns = @JoinColumn(name = "ROL_ID", foreignKey = @ForeignKey(name = "FK_USR_ROL_ROLE"), referencedColumnName = "ROL_ID", nullable = false, updatable = false), joinColumns = @JoinColumn(name = "USR_ID", foreignKey = @ForeignKey(name = "FK_USR_ROL_USER"), referencedColumnName = "USR_ID", nullable = false, updatable = false))
	private Collection<Role> roles;

	@ManyToMany
	@JoinTable(name = "users_jobs", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID"), inverseJoinColumns = @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID"))
	private Collection<Job> jobs;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private Long profileUpdateCount = 0L;

	public Long getDesiredInternshipPeriodDays() {
		return ChronoUnit.DAYS.between(internshipStartDate, internshipEndDate);
	}

	public boolean compareUserForUpdate(User other){
		if (other == null || getClass() != other.getClass()) {
			return false;
		} else {
			return id.equals(other.id) &&
					civility.equals(other.civility) &&
					email.equals(other.email) &&
					firstName.equals(other.firstName) &&
					name.equals(other.name) &&
					phone.equals(other.phone) &&
					postalCode == other.postalCode &&
					dateOfBirth.equals(other.dateOfBirth) &&
					internshipStartDate.equals(other.internshipStartDate) &&
					internshipEndDate.equals(other.internshipEndDate) &&
                    Arrays.equals(jobs.stream().map(Job::getId).toArray(), other.jobs.stream().map(Job::getId).toArray()) &&
					Objects.equals(internshipPeriod, other.internshipPeriod) &&
					Objects.equals(internStatus.getId(), other.internStatus.getId()) &&
					Objects.equals(diploma, other.diploma);
		}
	}

	public boolean filesAreEqual(Collection<FileDb> files, Collection<FileDb> otherFiles) {
		FileDb cvFile1 = new FileDb();
		FileDb cvFile2 = new FileDb();
		FileDb coverLetterFile1 = new FileDb();
		FileDb coverLetterFile2 = new FileDb();

		for (FileDb file : files) {
			if (file.getType() == FileTypeEnum.CV) {
				cvFile1 = file;
			} else if (file.getType() == FileTypeEnum.COVER_LETTER) {
				coverLetterFile1 = file;
			}
		}

		for (FileDb file : otherFiles) {
			if (file.getType() == FileTypeEnum.CV) {
				cvFile2 = file;
			} else if (file.getType() == FileTypeEnum.COVER_LETTER) {
				coverLetterFile2 = file;
			}
		}

        return Arrays.equals(cvFile1.getData(), cvFile2.getData()) &&
				Arrays.equals(coverLetterFile1.getData(), coverLetterFile2.getData());
	}
}
