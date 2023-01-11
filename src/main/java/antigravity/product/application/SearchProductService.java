package antigravity.product.application;

import antigravity.product.repository.ProductResponse;
import antigravity.product.repository.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchProductService {
    private final ProductQueryRepository productQueryRepository;

    public SearchResponse<ProductResponse> searchProduct(Boolean liked, Long userId, Integer page, Integer size) {
        return productQueryRepository.search(liked, userId, page,
                size);
    }
}
