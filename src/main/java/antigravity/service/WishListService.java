package antigravity.service;

import antigravity.payload.CreateWishListRequest;
import antigravity.payload.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListService {
    Long save(CreateWishListRequest request);

    Page<ProductResponse> getPage(String userId, boolean liked, Pageable pageable);
}
