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
) engine=InnoDB;

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
) engine=InnoDB;

alter table money_book
    add constraint fk_money_book_user_id_users_id
        foreign key (user_id)
            references users (id);
