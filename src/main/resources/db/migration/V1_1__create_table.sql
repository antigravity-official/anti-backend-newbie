create table product
(
    id         bigint auto_increment primary key,
    name       varchar(255)   not null,
    price      decimal(19, 2) not null,
    quantity   int            not null,
    sku        varchar(255)   not null,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    deleted_at datetime(6) null
);

create table users
(
    id         bigint auto_increment primary key,
    email      varchar(255) not null,
    name       varchar(255) null,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    deleted_at datetime(6) null
);

create table cart
(
    id         bigint auto_increment primary key,
    product_id bigint not null,
    user_id    bigint not null,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    constraint duplication
        unique (user_id, product_id),
    constraint FK3d704slv66tw6x5hmbm6p2x3u
        foreign key (product_id) references product (id),
    constraint FKg5uhi8vpsuy0lgloxk2h4w5o6
        foreign key (user_id) references users (id)
);

