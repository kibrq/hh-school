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
    views_count integer
);

CREATE TABLE vacancy (
    vacancy_id      integer primary key,
    name            varchar(100) not null,
    date_create     timestamp,
    salary_to       integer,
    salary_from     integer,
    salary_currency varchar(16),
    salary_gross    boolean,
    area_id         integer references area (area_id),
    employer_id     integer references employer (employer_id),
    created_at      timestamp,
    comment         varchar(100),
    views_count     integer
)