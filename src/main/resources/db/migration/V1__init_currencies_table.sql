drop table if exists currencies;
create table currencies
(
    id      SERIAL PRIMARY KEY,
    date_time TIMESTAMP NOT NULL
);
