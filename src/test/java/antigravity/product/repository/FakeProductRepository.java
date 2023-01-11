package antigravity.product.repository;

import antigravity.product.application.ProductQueryRepository;
import antigravity.product.application.ProductRepository;
import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FakeProductRepository implements ProductRepository, ProductQueryRepository {

    private final List<Product> products = new ArrayList<>();
    private long incrementProductLikeId = 1;
    private final List<ProductLike> productLikes = new ArrayList<>();

    private long incrementProductViewId = 1;

    private final List<ProductView> productViews = new ArrayList<>();

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<ProductLike> getProductLikes() {
        return new ArrayList<>(productLikes);
    }

    public List<ProductView> getProductViews() {
        return new ArrayList<>(productViews);
    }

    public FakeProductRepository() {
        initProduct();
    }

    private void initProduct() {
        for (int i = 1; i < 21; i++) {
            Product product = Product.builder()
                    .id((long) i)
                    .sku("G20000000" + String.format("%02d", i))
                    .name("상품 " + i)
                    .price(new BigDecimal(i * 1000))
                    .quantity(i)
                    .build();
            products.add(product);
        }
        LocalDateTime deletedAt = LocalDateTime.of(2023, 1, 11, 16, 52, 57);
        for (int i = 21; i < 24; i++) {
            Product product = Product.builder()
                    .id((long) i)
                    .sku("G20000000" + String.format("%02d", i))
                    .name("상품 " + i)
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

    @Override
    public SearchResponse<ProductResponse> search(Boolean liked, Long userId, Integer page,
            Integer size) {
        if (liked == null) {
            return searchAll(userId, page, size);
        }

        if (liked.equals(true)) {
            return searchWithLiked(userId, page, size);
        }
        return searchWithDidNotLiked(userId, page, size);
    }

    private SearchResponse<ProductResponse> searchAll(Long userId, Integer page, Integer size) {
        List<Product> foundProducts = products.stream().filter(p -> !p.isDeleted())
                .sorted(Comparator.comparing(Product::getId).reversed()).skip(getOffset(page, size))
                .limit(size).collect(
                        Collectors.toList());
        long total = products.stream().filter(p -> !p.isDeleted()).count();

        return getSearchResponse(userId, page, size, foundProducts, total);
    }

    private SearchResponse<ProductResponse> searchWithLiked(Long userId, Integer page,
            Integer size) {
        List<Long> productIdsWithLiked = productLikes.stream()
                .filter(pl -> pl.getUserId().equals(userId)).map(
                        ProductLike::getProductId).collect(Collectors.toList());

        List<Product> foundProducts = products.stream()
                .filter(p -> productIdsWithLiked.contains(p.getId())).filter(p -> !p.isDeleted())
                .sorted(Comparator.comparing(Product::getId).reversed()).skip(getOffset(page, size))
                .limit(size).collect(
                        Collectors.toList());
        long total = products.stream().filter(p -> productIdsWithLiked.contains(p.getId()))
                .filter(p -> !p.isDeleted()).count();

        return getSearchResponse(userId, page, size, foundProducts, total);

    }

    private SearchResponse<ProductResponse> searchWithDidNotLiked(Long userId, Integer page,
            Integer size) {
        List<Long> productIdsWithLiked = productLikes.stream()
                .filter(pl -> pl.getUserId().equals(userId)).map(
                        ProductLike::getProductId).collect(Collectors.toList());

        List<Product> foundProducts = products.stream()
                .filter(p -> !productIdsWithLiked.contains(p.getId())).filter(p -> !p.isDeleted())
                .sorted(Comparator.comparing(Product::getId).reversed()).skip(getOffset(page, size))
                .limit(size).collect(
                        Collectors.toList());
        long total = products.stream().filter(p -> !productIdsWithLiked.contains(p.getId()))
                .filter(p -> !p.isDeleted()).count();

        return getSearchResponse(userId, page, size, foundProducts, total);
    }

    private SearchResponse<ProductResponse> getSearchResponse(Long userId,
            Integer page, Integer size, List<Product> foundProducts, long total) {
        List<ProductResponse> content = foundProducts.stream().map(p -> {
            Long productId = p.getId();
            List<ProductLike> foundProductLikes = getProductLikesByProductId(productId);
            Integer totalViews = getTotalViewsByProductId(productId);
            return ProductResponse.of(userId, p, foundProductLikes, totalViews);
        }).collect(Collectors.toList());

        return new SearchResponse<>(content, size, page - 1, total);
    }

    private static long getOffset(Integer page, Integer size) {
        return ((long) page - 1L) * (long) size;
    }

    private List<ProductLike> getProductLikesByProductId(Long productId) {
        return productLikes.stream().filter(pl -> pl.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    private Integer getTotalViewsByProductId(Long productId) {
        return productViews.stream().filter(pv -> pv.getProductId().equals(productId)).collect(
                Collectors.toList()).size();
    }
}
