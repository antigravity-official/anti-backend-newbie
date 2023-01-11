package antigravity.router;

import antigravity.payload.ProductResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

interface ProductResponseMaker {
    boolean isSupport(Boolean like);

    List<ProductResponse> getProductList(Long memberId, Pageable pageable);
}
