package antigravity.router;

import antigravity.payload.ProductResponse;
import antigravity.repository.LikeHistoryRepository;
import antigravity.repository.ProductRepository;
import antigravity.service.ProductViewService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeResponseMaker implements ProductResponseMaker {

    private final ProductRepository productRepository;
    private final LikeHistoryRepository likeHistoryRepository;
    private final ProductViewService productViewService;

    @Override
    public boolean isSupport(Boolean like) {
        return like != null && like;
    }

    @Override
    public List<ProductResponse> getProductList(Long memberId, Pageable pageable) {
        return productRepository.getLikeProduct(memberId, pageable).stream()
                .map(product -> new ProductResponse(product
                        , likeHistoryRepository.getTotalLike(product.getId())
                        , productViewService.getViewCount(product.getId())))
                .collect(Collectors.toUnmodifiableList());
    }
}
