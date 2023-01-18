package antigravity.application;

public interface ProductViewCacheManager {

    Integer getViewCount(Long productId);
    Long incrementProductViewCount(Long productId);

}
