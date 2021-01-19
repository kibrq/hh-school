CREATE TABLE area (
  area_id   serial primary key,
  area_name text
);

CREATE TABLE cv (
  cv_id   serial primary key,
  cv_text text
);

CREATE TABLE employer (
  employer_id   serial primary key,
  employer_name text   not null
);

CREATE TABLE vacancy (
  vacancy_id         serial  primary key,
  employer_id        integer not null references employer (employer_id),
  position_name      text    not null,
  creation_date      date    not null,
  area_id            integer not null references area     (area_id),
  compensation_from  integer,
  compensation_to    integer,
  compensation_gross boolean
);

CREATE TABLE response (
  response_id   serial  primary key,
  vacancy_id    integer not null references vacancy (vacancy_id),
  cv_id         integer not null references cv      (cv_id),
  area_id       integer not null references area    (area_id),
  creation_date date    not null
);

