create table job_offer (jbo_id  bigserial not null, jbo_active boolean default true not null, jbo_description varchar(255), jbo_start date, jbo_title varchar(255), primary key (jbo_id));