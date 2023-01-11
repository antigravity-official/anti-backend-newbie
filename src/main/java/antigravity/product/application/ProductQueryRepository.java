package antigravity.product.application;

import antigravity.product.repository.ProductResponse;
import antigravity.product.repository.SearchResponse;

public interface ProductQueryRepository {

    SearchResponse<ProductResponse> search(Boolean liked, Long userId, Integer page, Integer size);

}
