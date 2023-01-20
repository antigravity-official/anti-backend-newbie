DROP TABLE `product_like` IF EXISTS;
DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;

CREATE TABLE `product`
(
    `id`         bigint(20)     NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)    NOT NULL,
    `updated_at` datetime(6)    NOT NULL,
    `name`       varchar(255)   NOT NULL,
    `price`      decimal(19, 2) NOT NULL,
    `quantity`   int(11)        NOT NULL,
    `sku`        varchar(255)   NOT NULL,
    `viewed`     int(11)        NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`    bigint(20)   NOT NULL AUTO_INCREMENT,
    `email` varchar(255) NOT NULL,
    `name`  varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `product_like`
(
    `product_like_id`     bigint(20) NOT NULL AUTO_INCREMENT,
    `product_like_status` int(11)    NOT NULL,
    `product_id`          bigint(20) NOT NULL,
    `user_id`             bigint(20) NOT NULL,
    PRIMARY KEY (`product_like_id`)
);

ALTER TABLE `product_like`
    ADD CONSTRAINT FK_PRODUCT_LIKE_ON_PRODUCT FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `product_like`
    ADD CONSTRAINT FK_PRODUCT_LIKE_ON_USER FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);