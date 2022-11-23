package antigravity.domain;

import antigravity.entity.Customer;
import antigravity.entity.LikedProduct;
import antigravity.entity.Product;

import java.math.BigDecimal;

public class LikedProductTestBuilder {
    public static LikedProduct createLikedProduct0() {
        Product product
                = Product.builder()
                .id(1L)
                .sku("G2000000019")
                .name("No1. 더핏세트")
                .price(BigDecimal.valueOf(42800))
                .quantity(10)
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .email("'user3@antigravity.kr'")
                .name("회원3")
                .build();

        return LikedProduct.createLikedProduct(product, customer);
    }

    public static LikedProduct createLikedProduct1() {
        Product product
                = Product.builder()
                .id(1L)
                .sku("G2000000019")
                .name("No1. 더핏세트")
                .price(BigDecimal.valueOf(42800))
                .quantity(10)
                .build();

        Customer customer = Customer.builder()
                .id(2L)
                .email("'user3@antigravity.kr'")
                .name("회원3")
                .build();

        return LikedProduct.createLikedProduct(product, customer);
    }
}
