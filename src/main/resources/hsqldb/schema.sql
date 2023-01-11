DROP TABLE `liked_product` IF EXISTS;
DROP TABLE `product` IF EXISTS;
DROP TABLE `user` IF EXISTS;

CREATE TABLE `product`
(
     id         bigint(20) NOT NULL AUTO_INCREMENT,
    sku       varchar(60)             DEFAULT '' COMMENT '상품 식별값',
    name       varchar(125)   NOT NULL COMMENT '상품명',
    price      decimal(12, 2) NOT NULL COMMENT '가격',
    quantity   int            NOT NULL COMMENT '재고량',
    created_at datetime       NOT NULL DEFAULT current_timestamp(),
    updated_at datetime                DEFAULT current_timestamp(),
    deleted_at datetime                DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE `user`
(
    id         bigint(20) NOT NULL AUTO_INCREMENT,
    email      varchar(120) NOT NULL DEFAULT '',
    name       varchar(45)           DEFAULT '',
    created_at datetime     NOT NULL DEFAULT current_timestamp(),
    deleted_at datetime              DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE `liked_product`
(
    id          bigint(20) NOT NULL AUTO_INCREMENT,
    product_id  bigint(20) NOT NULL,
    user_id     bigint(20) NOT NULL,
    like_status boolean  NOT NULL DEFAULT false,
    created_at  datetime NOT NULL DEFAULT current_timestamp(),
    updated_at  datetime          DEFAULT current_timestamp(),
    CONSTRAINT product_id_FK FOREIGN KEY (product_id)
        REFERENCES `product` (id) ON DELETE CASCADE,
    CONSTRAINT user_id_FK FOREIGN KEY (user_id)
        REFERENCES `user` (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uniq__user_id__product_id ON  `liked_product`(user_id, product_id);



CREATE TABLE `product_view_count`
(
    product_id bigint(20) NOT NULL AUTO_INCREMENT,
    view_count bigint NOT NULL default 1,
    CONSTRAINT product_liked_id_FK FOREIGN KEY (product_id)
        REFERENCES `product` (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX uniq__view_count__product_id ON  `product_view_count`(product_id);
