delete from transaction_status;
delete from limit_status;

insert into limit_status(id, account_number, expense_category, limit_currency, limit_value, received_time) values
    (1, 322, 'product', 'USD', '2000', '2022-12-29 12:55:01.933580');

insert into transaction_status(id, account_from, account_to, currency_shortname, expense_category, limit_exceeded, received_time, sum, limit_status) values
    (1, 322, 13131, 'KZT', 'service', true, '2022-12-29 12:51:00.115324', 46000.00, 1);