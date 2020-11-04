drop table if exists log_call_currencies;
create table log_call_currencies
(
    id      SERIAL PRIMARY KEY,
    date_time TIMESTAMP NOT NULL
);
