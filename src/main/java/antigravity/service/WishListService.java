package antigravity.service;

import antigravity.payload.CreateWishListRequest;
import antigravity.payload.ProductResponse;
import org.springframework.data.domain.Page;

public interface WishListService {
    Long save(CreateWishListRequest request);

    Page<ProductResponse> getPage();
}
