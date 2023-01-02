CREATE TABLE IF NOT EXISTS limit_status(
    id SERIAL NOT NULL CONSTRAINT limit_status_pkey PRIMARY KEY,
    account_number BIGINT,
    limit_value BIGINT,
    expense_category VARCHAR,
    received_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transaction_status(
    id SERIAL NOT NULL,
    limit_id BIGINT CONSTRAINT transaction_status_limit REFERENCES limit_status(id),
    account_from BIGINT,
    account_to BIGINT,
    currency_shortname VARCHAR,
    sum NUMERIC,
    expense_category VARCHAR,
    received_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS exchange_rate_status(
     id SERIAL NOT NULL,
     rate_value NUMERIC(6,2),
     received_time TIMESTAMP
);