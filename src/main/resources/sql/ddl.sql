create table users
(
    id         bigint auto_increment
        primary key,
    created_at datetime     null,
    deleted    bit          not null,
    deleted_at datetime     null,
    updated_at datetime     null,
    email      varchar(255) null,
    name       varchar(255) null,
    password   varchar(255) null
);

create table money_book
(
    id         bigint auto_increment
        primary key,
    created_at datetime     null,
    deleted    bit          not null,
    deleted_at datetime     null,
    updated_at datetime     null,
    memo       varchar(255) null,
    money      bigint       null,
    user_id    bigint       null,
    constraint FKh5ndt01gs0beljrgw6hwc836w
        foreign key (user_id) references users (id)
);

