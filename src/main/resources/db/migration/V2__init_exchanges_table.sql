drop table if exists exchanges;
create table exchanges
(
    id      SERIAL PRIMARY KEY,
    amount NUMERIC NOT NULL,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    amount_after_exchange NUMERIC NOT NULL,
    date_time TIMESTAMP NOT NULL
);
