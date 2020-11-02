drop table if exists exchange_rates;
create table exchange_rates
(
    id      SERIAL PRIMARY KEY,
    currency VARCHAR(3) NOT NULL,
    rate NUMERIC NOT NULL,
    date_time TIMESTAMP NOT NULL
);
