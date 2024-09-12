-- entreprendre_ensemble.activities definition

-- Drop table

-- DROP TABLE activities;

CREATE TABLE activities (
	activity_id bigserial NOT NULL,
	activity_name varchar(255) NOT NULL,
	CONSTRAINT activities_pkey PRIMARY KEY (activity_id)
);




-- entreprendre_ensemble.cities definition

-- Drop table

-- DROP TABLE cities;

CREATE TABLE cities (
	city_id bigserial NOT NULL,
	city_name varchar(255) NOT NULL,
	city_postal_code varchar(255) NOT NULL,
	CONSTRAINT cities_pkey PRIMARY KEY (city_id)
);


-- entreprendre_ensemble.interns_status definition

-- Drop table

-- DROP TABLE interns_status;

CREATE TABLE interns_status (
	status_id bigserial NOT NULL,
	status_name varchar(255) NOT NULL,
	CONSTRAINT interns_status_pkey PRIMARY KEY (status_id)
);


-- entreprendre_ensemble.jobs definition

-- Drop table

-- DROP TABLE jobs;

CREATE TABLE jobs (
	job_id bigserial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT jobs_pkey PRIMARY KEY (job_id)
);


-- entreprendre_ensemble.roles definition

-- Drop table

-- DROP TABLE roles;

CREATE TABLE roles (
	rol_id bigserial NOT NULL,
	rol_role varchar(255) NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (rol_id)
);


-- entreprendre_ensemble.companies definition

-- Drop table

-- DROP TABLE companies;

CREATE TABLE companies (
	company_id bigserial NOT NULL,
	company_contact_firstname varchar(255) NULL,
	company_contact_lastname varchar(255) NULL,
	company_contact_mail varchar(255) NULL,
	company_contact_num varchar(255) NULL,
	company_description varchar(1024) NULL,
	company_logo bytea NULL,
	company_name varchar(255) NOT NULL,
	company_siret varchar(14) NULL,
	company_type varchar(255) NULL,
	company_address varchar(255) NULL,
	company_desired_interns_number varchar(255) NULL,
	company_accepts_long_paid_internship bool NULL,
	company_activated bool NOT NULL DEFAULT true,
	company_department varchar(255) NULL,
	company_region varchar(255) NULL,
	company_epci varchar(255) NULL,
	company_fix_contact_num varchar(255) NULL,
	company_website varchar(255) NULL,
	company_minor_accepted bool NOT NULL DEFAULT false,
	city_id int8 NULL,
	CONSTRAINT companies_pkey PRIMARY KEY (company_id),
	CONSTRAINT fkie62fn0rnmuenspjh789l8e9f FOREIGN KEY (city_id) REFERENCES cities(city_id)
);


-- entreprendre_ensemble.companies_activities definition

-- Drop table

-- DROP TABLE companies_activities;

CREATE TABLE companies_activities (
	company_id int8 NOT NULL,
	activity_id int8 NOT NULL,
	CONSTRAINT companies_activities_pkey PRIMARY KEY (company_id, activity_id),
	CONSTRAINT fk9s5bkb1xyxh9pcs9c7h33sy7b FOREIGN KEY (company_id) REFERENCES companies(company_id),
	CONSTRAINT fkqxlm5u13u9maar5dp4sn0la2g FOREIGN KEY (activity_id) REFERENCES activities(activity_id)
);


-- entreprendre_ensemble.companies_searched_activities definition

-- Drop table

-- DROP TABLE companies_searched_activities;

CREATE TABLE companies_searched_activities (
	company_id int8 NOT NULL,
	activity_id int8 NOT NULL,
	CONSTRAINT companies_searched_activities_pkey PRIMARY KEY (company_id, activity_id),
	CONSTRAINT fkb6401k5dcu2fuhgo7tctc51hl FOREIGN KEY (company_id) REFERENCES companies(company_id),
	CONSTRAINT fkpsmeps3gprtb95wcm4uyoxmyl FOREIGN KEY (activity_id) REFERENCES activities(activity_id)
);


-- entreprendre_ensemble.companies_searched_jobs definition

-- Drop table

-- DROP TABLE companies_searched_jobs;

CREATE TABLE companies_searched_jobs (
	company_id int8 NOT NULL,
	job_id int8 NOT NULL,
	CONSTRAINT companies_searched_jobs_pkey PRIMARY KEY (company_id, job_id),
	CONSTRAINT fk953689t919mx1uj5rp02povm9 FOREIGN KEY (job_id) REFERENCES jobs(job_id),
	CONSTRAINT fkrso8wvgkfgnha9lw1obmpqjpk FOREIGN KEY (company_id) REFERENCES companies(company_id)
);


-- entreprendre_ensemble.interns_type definition

-- Drop table

-- DROP TABLE interns_type;

CREATE TABLE interns_type (
	intern_type_id bigserial NOT NULL,
	periods _text NULL,
	company_id int8 NOT NULL,
	status_id int8 NULL,
	CONSTRAINT interns_type_pkey PRIMARY KEY (intern_type_id),
	CONSTRAINT fkk2q13jt303ei0l8k04mswswft FOREIGN KEY (company_id) REFERENCES companies(company_id),
	CONSTRAINT fknmyu0d5hx2mshhecml6crkcqm FOREIGN KEY (status_id) REFERENCES interns_status(status_id)
);


-- entreprendre_ensemble.users definition

-- Drop table

-- DROP TABLE users;

CREATE TABLE users (
	usr_id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamp NULL,
	last_modified_by varchar(255) NULL,
	last_modified_date timestamp NULL,
	civility varchar(255) NOT NULL,
	date_of_birth date NOT NULL,
	diploma varchar(255) NULL,
	email varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	internship_period varchar(255) NULL,
	"name" varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	phone varchar(255) NOT NULL,
	postal_code int4 NULL,
	status_id int8 NULL,
	activated bool NOT NULL DEFAULT true,
	reset_password_token varchar(255) NULL,
	internship_end_date date NULL,
	internship_start_date date NULL,
	CONSTRAINT users_pkey PRIMARY KEY (usr_id),
	CONSTRAINT fkdytl7akxknisjvggiefy40dxu FOREIGN KEY (status_id) REFERENCES interns_status(status_id)
);


-- entreprendre_ensemble.users_activities definition

-- Drop table

-- DROP TABLE users_activities;

CREATE TABLE users_activities (
	usr_id int8 NOT NULL,
	activity_id int8 NOT NULL,
	CONSTRAINT users_activities_pkey PRIMARY KEY (usr_id, activity_id),
	CONSTRAINT fkk2l99v1ulpynvi93fyrptad7b FOREIGN KEY (activity_id) REFERENCES activities(activity_id),
	CONSTRAINT fklmkgaowj8e6p6g3aevk1fayqb FOREIGN KEY (usr_id) REFERENCES users(usr_id)
);


-- entreprendre_ensemble.users_applied_companies definition

-- Drop table

-- DROP TABLE users_applied_companies;

CREATE TABLE users_applied_companies (
	usr_id int8 NOT NULL,
	company_id int8 NOT NULL,
	CONSTRAINT fk1dnyivtparsftrbp1l706jp0l FOREIGN KEY (company_id) REFERENCES companies(company_id),
	CONSTRAINT fkdjewplluadjsttemqtliujd6a FOREIGN KEY (usr_id) REFERENCES users(usr_id)
);


-- entreprendre_ensemble.users_jobs definition

-- Drop table

-- DROP TABLE users_jobs;

CREATE TABLE users_jobs (
	usr_id int8 NOT NULL,
	job_id int8 NOT NULL,
	CONSTRAINT users_jobs_pkey PRIMARY KEY (usr_id, job_id),
	CONSTRAINT fkitap0feisnvo40xtiilkwh12t FOREIGN KEY (usr_id) REFERENCES users(usr_id),
	CONSTRAINT fkp4l1fiytkjtn49mrqa8987uk7 FOREIGN KEY (job_id) REFERENCES jobs(job_id)
);


-- entreprendre_ensemble.users_roles definition

-- Drop table

-- DROP TABLE users_roles;

CREATE TABLE users_roles (
	usr_id int8 NOT NULL,
	rol_id int8 NOT NULL,
	CONSTRAINT fk_usr_rol_role FOREIGN KEY (rol_id) REFERENCES roles(rol_id),
	CONSTRAINT fk_usr_rol_user FOREIGN KEY (usr_id) REFERENCES users(usr_id)
);


-- entreprendre_ensemble.files definition

-- Drop table

-- DROP TABLE files;

CREATE TABLE files (
	id bigserial NOT NULL,
	"data" bytea NULL,
	"name" varchar(255) NULL,
	"type" varchar(255) NULL,
	usr_id int8 NOT NULL,
	CONSTRAINT files_pkey PRIMARY KEY (id),
	CONSTRAINT fksa556bdok3f7a7c2f7chhs1kx FOREIGN KEY (usr_id) REFERENCES users(usr_id)
);