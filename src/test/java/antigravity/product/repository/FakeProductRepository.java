package antigravity.product.repository;

import antigravity.product.application.ProductRepository;
import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private long incrementProductLikeId = 1;
    private final List<ProductLike> productLikes = new ArrayList<>();

    private long incrementProductViewId = 1;

    private final List<ProductView> productViews = new ArrayList<>();

    public List<ProductLike> getProductLikes() {
        return productLikes;
    }

    public List<ProductView> getProductViews() {
        return productViews;
    }

    public FakeProductRepository() {
        initProduct();
    }

    private void initProduct() {
        for (int i = 1; i < 21; i++) {
            Product product = Product.builder()
                    .id((long) i)
                    .sku("G20000000"+String.format("%02d", i))
                    .name("상품 "+i)
                    .price(new BigDecimal(i * 1000))
                    .quantity(i)
                    .build();
            products.add(product);
        }
        LocalDateTime deletedAt = LocalDateTime.of(2023, 1, 11, 16, 52, 57);
        for (int i = 21; i < 24; i++) {
            Product product = Product.builder()
                    .id((long) i)
                    .sku("G20000000"+String.format("%02d", i))
                    .name("상품 "+i)
                    .price(new BigDecimal(i * 1000))
                    .quantity(i)
                    .deletedAt(deletedAt)
                    .build();
            products.add(product);
        }
    }

    @Override
    public void like(Long productId, Long userId) {
        checkAlreadyLikedProduct(productId, userId);
        ProductLike productLike = ProductLike.builder()
                .id(incrementProductLikeId++)
                .productId(productId)
                .userId(userId)
                .build();
        productLikes.add(productLike);
    }

    @Override
    public Product getById(Long productId) {
        return products.stream().filter(p -> p.getId().equals(productId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Override
    public void increseViewsByProductId(Long productId) {
        ProductView productView = ProductView.builder()
                .id(incrementProductViewId++)
                .productId(productId)
                .build();
        productViews.add(productView);
    }

    private void checkAlreadyLikedProduct(Long productId, Long userId) {
        if (productLikes.stream().anyMatch(
                pl -> pl.getProductId().equals(productId) && pl.getUserId().equals(userId))) {
            throw new IllegalStateException("이미 찜한 상품입니다.");
        }
    }
}
