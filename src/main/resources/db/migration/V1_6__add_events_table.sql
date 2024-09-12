create table event_locations (event_location_id  bigserial not null, event_location_address varchar(255) not null, event_location_city varchar(255) not null, event_location_name varchar(255) not null, event_location_postal_code varchar(255) not null, primary key (event_location_id));
create table events (event_id  bigserial not null, event_active BOOLEAN DEFAULT FALSE not null, event_description TEXT, event_ended_at timestamp not null, event_image TEXT not null, event_name varchar(255) not null, event_started_at timestamp not null, event_type varchar(255) not null, event_location_id int8 not null, primary key (event_id));
create table users_applied_events (usr_event_id  bigserial not null, created_at timestamp not null, evt_id int8 not null, usr_id int8 not null, primary key (usr_event_id));

alter table events add constraint FKctfrbhft24f1q82vo9aslgksf foreign key (event_location_id) references event_locations;
alter table users_applied_events add constraint FKavqfbx8k8ecwatt96qvttgcyh foreign key (evt_id) references events;
alter table users_applied_events add constraint FKsxp01hcn90al4gmqnbq7m2rgt foreign key (usr_id) references users;