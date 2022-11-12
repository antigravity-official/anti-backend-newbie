package antigravity.repository;

import antigravity.payload.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListRepositoryCustom {

    Page<ProductResponse> getPage(String userId, Pageable pageable);
}
