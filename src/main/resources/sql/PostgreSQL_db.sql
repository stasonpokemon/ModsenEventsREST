drop database if exists modsen_events;
create database if not exists modsen_events;

\c modsen_events

create table events(
                       id BIGINT generated by default as identity not null,
                       topic varchar(50) not null,
                       description character varying(300) not null,
                       organizer character varying(50) not null,
                       event_time timestamp not null default now(),
                       location character varying(100) not null,
                       created_at timestamp not null,
                       updated_at timestamp not null,
                       constraint events_pk PRIMARY KEY (id)
);

insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('Spring REST', 'Today we will talk about Spring REST application', 'MODSEN Group', '2022-11-20T13:00:00.000+00:00', 'Vitebsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('Spring MVC', 'Today we will talk about Spring MVC application', 'MODSEN Group', '2022-11-22T13:00:00.000+00:00', 'Vitebsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('PostgreSql', 'Today we will talk about PostgreSQL database', 'MODSEN Group', '2022-11-22T15:00:00.000+00:00','Vitebsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('PostgreSql', 'Today we will talk about PostgreSQL database', 'MODSEN Group', '2022-11-26T13:00:00.000+00:00','Minsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('PostgreSql', 'Today we will talk about PostgreSQL database', 'MODSEN Group', '2022-12-02T13:00:00.000+00:00','Poland', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('MySql', 'Today we will talk about MySql database', 'MODSEN Group', '2022-12-06T13:30:00.000+00:00','Minsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('SOLID', 'Today we will talk about SOLID', 'MODSEN Group', '2022-12-12T13:30:00.000+00:00','Vitebsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('Hibernate', 'Today we will talk about Hibernate', 'MODSEN Group', '2022-12-22T15:00:00.000+00:00','Minsk', now(), now());
