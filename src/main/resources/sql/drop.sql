
alter table money_book
drop
foreign key fk_money_book_user_id_users_id;

drop table if exists money_book;

drop table if exists users;
