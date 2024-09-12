create table visits (id  bigserial not null, created_date timestamp, usr_id int8, primary key (id));

alter table visits add constraint FK34ld7esrajm00v3d5yt9ibxav foreign key (usr_id) references users;