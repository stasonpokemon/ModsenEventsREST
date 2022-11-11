create table events(
                       id BIGINT auto_increment not null,
                       topic varchar(50) not null,
                       description varchar(300) not null,
                       organizer varchar(50) not null,
                       event_time timestamp not null default now(),
                       location varchar(100) not null,
                       constraint events_pk PRIMARY KEY (id)
);
