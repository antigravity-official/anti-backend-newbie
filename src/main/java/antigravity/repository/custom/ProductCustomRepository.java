package antigravity.repository.custom;

import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearchCriteria;

import java.util.List;

public interface ProductCustomRepository {
    List<ProductResponse> findProductAndWishList(ProductSearchCriteria productSearchCriteria);
}
