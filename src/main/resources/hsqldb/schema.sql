DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;
DROP TABLE `basket` IF EXISTS;
DROP TABLE `product_info` IF EXISTS;

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
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `basket`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `liked`      boolean        DEFAULT FALSE COMMENT '찜 여부',
    `product_id` bigint     COMMENT '제품 id',
    user_id     bigint      COMMENT '유저 id',
    PRIMARY KEY (`id`)
);

CREATE TABLE `product_info`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `total_liked` int        NOT NULL COMMENT '상품이 받은 모든 찜 개수',
    `viewed`    int         NOT NULL COMMENT '상품 조회 수',
    `product_id` bigint(20)     COMMENT '제품 id',
    PRIMARY KEY (`id`)
)