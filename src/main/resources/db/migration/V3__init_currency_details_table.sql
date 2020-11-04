drop table if exists currency_details;
create table currency_details
(
    id      SERIAL PRIMARY KEY,
    currency VARCHAR(3) NOT NULL,
    bid_rate NUMERIC NOT NULL,
    ask_rate NUMERIC NOT NULL,
    date_time TIMESTAMP NOT NULL
);
