package antigravity.domain.repository;

import java.util.List;

public interface ProductViewRepository {

    Long findCountById(Long productId);

    List<Long> getProductIds();

    void updateViewCount(Long productId, Long viewCount);

}
