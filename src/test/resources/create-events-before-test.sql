truncate table events;
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('Spring REST', 'Today we will talk about Spring REST application', 'MODSEN Group', now(), 'Vitebsk', now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('MySql', 'Today we will talk about MySql database', 'MODSEN Group', '2022-12-22T10:13:00.000+00:00', 'Vitebsk',now(), now());
insert into events(topic, description, organizer, event_time, location, created_at, updated_at)
values ('PostgreSql', 'Today we will talk about PostgreSql database', 'MODSEN Group', '2022-12-02T10:13:00.000+00:00','Vitebsk', now(), now());
