DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;
DROP TABLE `dip_product` IF EXISTS;

CREATE TABLE `product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
    `viewed`   bigint(20)       NOT NULL DEFAULT 0 COMMENT '조회수',
    `created_at` datetime       NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime                DEFAULT current_timestamp(),
    `deleted_at` datetime                DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `dip_product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`      int            NOT NULL COMMENT '유저아이디',
    `product_id` bigint(20) NOT NULL COMMENT '제품아이디',
    PRIMARY KEY (`id`)
);