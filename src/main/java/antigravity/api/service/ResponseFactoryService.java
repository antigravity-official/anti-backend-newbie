package antigravity.api.service;

import antigravity.entity.Product;
import antigravity.payload.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseFactoryService {

    public ProductSearchResponse makeProductSearchResponseWithProduct(Product product, Boolean isLiked, Integer totalLiked) {
        return ProductSearchResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .viewed(product.getViewed())
                .createdAt(product.getCreatedAt().toString())
                .updatedAt(product.getUpdatedAt().toString())
                .liked(isLiked)
                .totalLiked(totalLiked)
                .build();
    }
}
