ALTER TABLE users_applied_companies ADD usr_company_id BIGSERIAL PRIMARY KEY;

ALTER TABLE users_applied_companies ADD created_date timestamp NULL;
ALTER TABLE interns_type ADD created_date timestamp NULL;

alter table users_applied_companies add constraint FK9teu9cmwbxlw3fr2a7itovi11 foreign key (usr_id) references users;
alter table users_applied_companies add constraint FKhk0va7xcyk13r7f6gb7tbytd9 foreign key (company_id) references companies;