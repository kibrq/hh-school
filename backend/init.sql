CREATE TABLE area (
    area_id integer primary key,
    name    varchar(100) not null
);

CREATE TABLE employer (
    employer_id integer primary key,
    name        varchar(100) not null,
    date_create timestamp,
    description varchar(256),
    area_id     integer      references area (area_id),
    comment     varchar(100),
    popularity  integer,
    views_count integer
);