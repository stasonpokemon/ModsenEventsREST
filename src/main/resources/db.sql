drop database if exists modson_events;
create database if not exists modson_events;
use modson_events;

create table events(
    id BIGINT auto_increment not null,
    topic varchar(50) not null,
    description varchar(300) not null,
    organizer varchar(50) not null,
    event_time timestamp not null default now(),
    location varchar(100) not null,
    constraint events_pk PRIMARY KEY (id)
);

insert into events(topic, description, organizer, event_time, location)
values ('Spring REST', 'Today we will talk about Spring REST application', 'MODSEN Group', now(), 'Vitebsk');
insert into events(topic, description, organizer, event_time, location)
values ('MySql', 'Today we will talk about MySql database', 'MODSEN Group', '18-11-2022', 'Vitebsk');