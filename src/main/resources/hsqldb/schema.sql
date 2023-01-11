DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;
DROP TABLE `liked` IF EXISTS;

CREATE TABLE `product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`        varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    `name`       varchar(125)   NOT NULL COMMENT '상품명',
    `price`      decimal(12, 2) NOT NULL COMMENT '가격',
    `quantity`   int            NOT NULL COMMENT '재고량',
    `hits` bigint(50) UNSIGNED NOT NULL DEFAULT 0 COMMENT '조회수',
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

CREATE TABLE `liked` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `productId` bigint(20) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  INDEX `fk_user_liked_idx` (`userId` ASC),
  INDEX `fk_user_product_idx` (`productId` ASC),
  UNIQUE INDEX `uk_user_product_idx` (`userId` ASC, `productId` ASC),
  CONSTRAINT `fk_user_liked`
    FOREIGN KEY (`userId`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_product`
    FOREIGN KEY (`productId`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);


