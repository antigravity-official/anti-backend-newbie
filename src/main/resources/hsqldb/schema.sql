DROP TABLE `product_view` IF EXISTS;
DROP TABLE `product_like` IF EXISTS;
DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;


CREATE TABLE `product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
    `created_at` datetime       NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime                DEFAULT current_timestamp(),
    `deleted_at` datetime                DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`         bigint(20)   NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime              DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `product_like`
(
    `id`         bigint(20)   NOT NULL AUTO_INCREMENT,
    `product_id` varchar(20)  NOT NULL DEFAULT '',
    `user_id`    varchar(20)           DEFAULT '',
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (id),
    FOREIGN KEY (`user_id`) REFERENCES `user` (id)
);

CREATE TABLE `product_view`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT,
    `product_id` varchar(20) NOT NULL DEFAULT '',
    `count` bigint(20)                DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (id),
)
