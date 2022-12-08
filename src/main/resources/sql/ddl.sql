
alter table money_book
drop
foreign key FKh5ndt01gs0beljrgw6hwc836w

drop table if exists money_book

drop table if exists users

create table money_book (
                            id bigint not null auto_increment,
                            created_at datetime(6),
                            deleted bit not null,
                            deleted_at datetime(6),
                            updated_at datetime(6),
                            memo varchar(255),
                            money bigint,
                            user_id bigint,
                            primary key (id)
) engine=InnoDB

create table users (
                       id bigint not null auto_increment,
                       created_at datetime(6),
                       deleted bit not null,
                       deleted_at datetime(6),
                       updated_at datetime(6),
                       email varchar(255),
                       expired_at bigint,
                       hashed_password varchar(255),
                       name varchar(255),
                       primary key (id)
) engine=InnoDB

alter table money_book
    add constraint FKh5ndt01gs0beljrgw6hwc836w
        foreign key (user_id)
            references users (id)