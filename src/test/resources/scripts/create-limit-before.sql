delete from limit_status;

insert into limit_status(id, account_number, expense_category, limit_currency, limit_value, received_time) values
    (1, 321, 'product', 'USD', '2000', null);

insert into limit_status(id, account_number, expense_category, limit_currency, limit_value, received_time) values
    (2, 321, 'service', 'USD', '2000', null);