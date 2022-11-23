package antigravity.domain;

import antigravity.entity.Product;

import java.math.BigDecimal;

public class ProductTestBuilder {
    public static Product createLikedProduct0() {
        Product product
                = Product.builder()
                .id(1L)
                .sku("G2000000019")
                .name("No1. 더핏세트")
                .price(BigDecimal.valueOf(42800))
                .quantity(10)
                .build();

        return product;
    }
}
