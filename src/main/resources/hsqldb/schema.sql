DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;
DROP TABLE `favorite` IF EXISTS;

CREATE TABLE `product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
    `viewed`     int            NOT NULL DEFAULT 0 COMMENT '상품 조회수',
    `totalLiked` bigint(20)              DEFAULT 0 COMMENT '상품이 받은 모든 찜 개수',
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

CREATE TABLE `favorite`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '좋아요 식별값',
    `product_id`    bigint(20) NOT NULL COMMENT '상품 식별값',
    `user_id`       bigint(20) NOT NULL COMMENT '유저 식별값',
    `created_at`    datetime   NOT NULL DEFAULT current_timestamp(),
    `delete_at`     datetime            DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);