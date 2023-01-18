package antigravity.payload;

import antigravity.entity.Basket;
import antigravity.entity.Product;
import antigravity.entity.ProductInfo;

public abstract class DataConverter {
    public static ProductResponse fromProductAndBasketAndProductInfo(Product product,
                                                                     Basket basket,
                                                                     ProductInfo productInfo)
    {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .liked(basket.getLiked())
                .totalLiked(productInfo.getTotalLiked())
                .viewed(productInfo.getViewed())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static ProductResponse fromProductAndBasketAndProductInfo(Product product,
                                                                     ProductInfo productInfo)
    {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .liked(false)
                .totalLiked(productInfo.getTotalLiked())
                .viewed(productInfo.getViewed())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
