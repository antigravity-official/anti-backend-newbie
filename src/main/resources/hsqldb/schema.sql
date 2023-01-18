DROP TABLE IF EXISTS `product_like`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
    `total_liked`int            NOT NULL DEFAULT 0,
    `viewed`     int            NOT NULL DEFAULT 0,
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
    `updated_at` datetime              DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `product_like`
(
    `id`     bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id`          bigint(20) NOT NULL,
    `user_id`             bigint(20) NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `product_like`
    ADD CONSTRAINT FK_PRODUCT_LIKE_ON_PRODUCT FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `product_like`
    ADD CONSTRAINT FK_PRODUCT_LIKE_ON_USER FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
