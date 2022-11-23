DROP TABLE `product` IF EXISTS;
DROP TABLE `customer` IF EXISTS;

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

//customer 로 변경한 이유는 user 가 h2 에서 키워드로 사용되고 있어서 에러가 발생함.
CREATE TABLE `customer`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `liked_product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(20) NOT NULL,
    `user_id`    bigint(20) NOT NULL,
     PRIMARY KEY (`id`),
     FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
     FOREIGN KEY (`user_id`) REFERENCES `customer` (`id`)
);


CREATE TABLE `product_statistics`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(20) NOT NULL COMMENT '상품아이디',
    `view_count` int NOT NULL DEFAULT 0 COMMENT '조회수',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime              DEFAULT NULL,
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);