package antigravity.application;

public interface ProductViewCacheManager {

    Integer getViewCount(Long productId);
    void incrementProductViewCount(Long productId);
}
