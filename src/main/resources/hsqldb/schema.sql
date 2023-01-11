DROP TABLE users IF EXISTS;
DROP TABLE likes IF EXISTS;
DROP TABLE product IF EXISTS;



CREATE TABLE product
(
    id       bigint(20) NOT NULL AUTO_INCREMENT,
    sku        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    name       varchar(125)   NOT NULL COMMENT '상품명',
    price      decimal(12, 2) NOT NULL COMMENT '가격',
    quantity   int            NOT NULL COMMENT '재고량',
    created_at datetime     NOT NULL DEFAULT current_timestamp(),
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

CREATE TABLE likes
(
    id        bigint(20) NOT NULL AUTO_INCREMENT,
    user_id      bigint(20) NOT NULL,
    product_id       bigint(20) NOT NULL ,
    liked   boolean,
    created_at datetime     NOT NULL DEFAULT current_timestamp(),
    updated_at datetime                DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) references product(id) on update cascade,
    UNIQUE KEY uk_product_user (user_id, product_id)
);
