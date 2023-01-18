create table product
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)    null,
    updated_at datetime(6)    null,
    deleted_at datetime(6)    null,
    name       varchar(255)   not null,
    price      decimal(19, 2) not null,
    quantity   int            not null,
    sku        varchar(255)   not null
);

create table users
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    deleted_at datetime(6)  null,
    email      varchar(255) not null,
    name       varchar(255) null
);

create table cart
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    product_id bigint      not null,
    user_id    bigint      not null,
    constraint duplication
        unique (user_id, product_id),
    constraint FK3d704slv66tw6x5hmbm6p2x3u
        foreign key (product_id) references product (id),
    constraint FKg5uhi8vpsuy0lgloxk2h4w5o6
        foreign key (user_id) references users (id)
);



CREATE TABLE product
(
    id         bigint(20) NOT NULL AUTO_INCREMENT,
    sku        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    name       varchar(125)   NOT NULL COMMENT '상품명',
    price      decimal(12, 2) NOT NULL COMMENT '가격',
    quantity   int            NOT NULL COMMENT '재고량',
    created_at datetime       NOT NULL DEFAULT current_timestamp(),
    updated_at datetime                DEFAULT current_timestamp(),
    deleted_at datetime                DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         bigint(20) NOT NULL AUTO_INCREMENT,
    email      varchar(120) NOT NULL DEFAULT '',
    name       varchar(45)           DEFAULT '',
    created_at datetime     NOT NULL DEFAULT current_timestamp(),
    deleted_at datetime              DEFAULT NULL,
    PRIMARY KEY (id)
);


create table cart
(
    id         bigint(20) not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    product_id bigint not null,
    user_id    bigint not null,
    primary key (id)
)


alter table cart
    add constraint FK3d704slv66tw6x5hmbm6p2x3u
        foreign key (product_id)
            references product (id)


alter table cart
    add constraint FKl70asp4l4w0jmbm1tqyofho4o
        foreign key (user_id)
            references user (id)

alter table cart
    add constraint duplication unique (user_id, product_id)
