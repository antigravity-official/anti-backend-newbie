DROP TABLE `product` IF EXISTS;
DROP TABLE `member` IF EXISTS;
DROP TABLE `heart` IF EXISTS;
DROP TABLE `view_count` IF EXISTS;

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

CREATE TABLE `member`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `heart`
(
    `member_id`  bigint(20) NOT NULL REFERENCES member (id) ON DELETE CASCADE,
    `product_id` bigint(20) NOT NULL REFERENCES product (id),
    PRIMARY KEY (`member_id`, `product_id`)
);

CREATE TABLE `view_count`
(
    `product_id` bigint(20) REFERENCES product (id) ON DELETE CASCADE,
    `views`      bigint(20) NOT NULL DEFAULT 0 COMMENT '조회수',
    PRIMARY KEY (`product_id`)
);