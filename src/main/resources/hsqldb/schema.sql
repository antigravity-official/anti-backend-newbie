DROP TABLE `wish` IF EXISTS;
DROP TABLE `product` IF EXISTS;
DROP TABLE `users` IF EXISTS;

CREATE TABLE `product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
    `view`       bigint(20)     UNSIGNED DEFAULT '0' NOT NULL COMMENT '조회수',
    `created_at` datetime       NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime                DEFAULT current_timestamp(),
    `deleted_at` datetime                DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `users`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `email`      varchar(120) NOT NULL DEFAULT '',
    `name`       varchar(45)           DEFAULT '',
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `wish`
(
    `user_id`     bigint(20)     NOT NULL COMMENT '사용자 ID (PK/FK)',
    `product_id`  bigint(20)     NOT NULL COMMENT '상품 ID (PK/FK)',
    `created_at` datetime        NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`user_id`, `product_id`),
    CONSTRAINT user_id_fk foreign key(user_id) references users (id),
    CONSTRAINT product_id_fk foreign key(product_id) references product (id)
);
