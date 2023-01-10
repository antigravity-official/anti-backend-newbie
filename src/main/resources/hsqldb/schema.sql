DROP TABLE `product` IF EXISTS;
DROP TABLE `users` IF EXISTS;

CREATE TABLE `product`
(
    `product_id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
        `view`   int            NOT NULL COMMENT '조회수',
    `created_at` datetime       NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime                DEFAULT current_timestamp(),
    `deleted_at` datetime                DEFAULT NULL,
    PRIMARY KEY (`product_id`)
);

CREATE TABLE `users`
(
    `users_id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`users_id`)
);
CREATE TABLE `wish`
(
    `wish_id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `is_wish`    varchar(5) NOT NULL DEFAULT '',
    `users_id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id`         bigint(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`wish_id`)
);