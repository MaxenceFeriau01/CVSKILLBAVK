package com.ensemble.entreprendre.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
	private boolean activated = true;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<FileDb> files;

	@ManyToMany
	@JoinTable(name = "users_applied_companies", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID"), inverseJoinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID"))
	private Collection<Company> appliedCompanies = new ArrayList<Company>();

	@ManyToMany
	@JoinTable(name = "users_roles", inverseJoinColumns = @JoinColumn(name = "ROL_ID", foreignKey = @ForeignKey(name = "FK_USR_ROL_ROLE"), referencedColumnName = "ROL_ID", nullable = false, updatable = false), joinColumns = @JoinColumn(name = "USR_ID", foreignKey = @ForeignKey(name = "FK_USR_ROL_USER"), referencedColumnName = "USR_ID", nullable = false, updatable = false))
	private Collection<Role> roles;

	@ManyToMany
	@JoinTable(name = "users_jobs", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID"), inverseJoinColumns = @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID"))
	private Collection<Job> jobs;

	@Column(nullable = false)
	private Integer updateProfil;

	public Long getDesiredInternshipPeriodDays() {
		return ChronoUnit.DAYS.between(internshipStartDate, internshipEndDate);
	}

	
	public boolean compareUserForUpdate(User other){
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		
		
		return 	id.equals(other.id) && 
			   	email.equals(other.email) &&
			   	firstName.equals(other.firstName) &&
			   	name.equals(other.name) &&
			   	phone.equals(other.phone) &&
			   	postalCode == other.postalCode &&
			   	dateOfBirth.equals(other.dateOfBirth) &&
			   	internshipStartDate.equals(other.internshipStartDate) &&
			    internshipEndDate.equals(other.internshipEndDate) && 
				internStatus.getId() == other.internStatus.getId();
				//filesAreEqual(files, other.files);
	}
	
	public boolean filesAreEqual(Collection<FileDb> files, Collection<FileDb> otherFiles) {
        FileDb cvFile1 = null;
        FileDb cvFile2 = null;
        FileDb coverLetterFile1 = null;
        FileDb coverLetterFile2 = null;

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

        if (cvFile1 == null || cvFile2 == null || coverLetterFile1 == null || coverLetterFile2 == null) {
            return false; 
        }

        return Arrays.equals(cvFile1.getData(), cvFile2.getData())
            && Arrays.equals(coverLetterFile1.getData(), coverLetterFile2.getData());
    }
	

}
