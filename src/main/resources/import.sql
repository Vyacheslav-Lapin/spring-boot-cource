set @client_id1 = RANDOM_UUID();
set @account_id1 = RANDOM_UUID();

insert into client (city, gender, name, id) values ('Kiev', 0, 'Adam Budzinski', @client_id1);
insert into account (balance, overdraft, dtype, id) values (-500, 1500, 'CheckingAccount', @account_id1);
insert into client_accounts (client_id, accounts_id) values (@client_id1, @account_id1);
update client set active_account_id = @account_id1 where id = @client_id1;

set @client_id2 = RANDOM_UUID();
set @account_id2 = RANDOM_UUID();
set @account_id3 = RANDOM_UUID();
insert into client (city, gender, name, id) values ('New York', 1, 'Anna Smith', @client_id2);
insert into account (balance, dtype, id) values (1000, 'SavingAccount', @account_id2);
insert into client_accounts (client_id, accounts_id) values (@client_id2, @account_id2);
insert into account (balance, overdraft, dtype, id) values (3000, 0, 'CheckingAccount', @account_id3);
insert into client_accounts (client_id, accounts_id) values (@client_id2, @account_id3);
update client set active_account_id = @account_id2 where id = @client_id2;

set @client_id3 = RANDOM_UUID();
set @account_id4 = RANDOM_UUID();
set @account_id5 = RANDOM_UUID();
insert into client (city, gender, name, id) values ('Moscow', 0, 'Jonny Bravo', @client_id3);
insert into account (balance, dtype, id) values (1000, 'SavingAccount', @account_id4);
insert into client_accounts (client_id, accounts_id) values (@client_id3, @account_id4);
insert into account (balance, overdraft, dtype, id) values (4000, 1000, 'CheckingAccount', @account_id5);
insert into client_accounts (client_id, accounts_id) values (@client_id3, @account_id5);
update client set active_account_id = @account_id4 where id = @client_id3;
