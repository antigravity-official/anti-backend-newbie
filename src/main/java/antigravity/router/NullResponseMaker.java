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
public class NullResponseMaker implements ProductResponseMaker {

    private final ProductRepository productRepository;
    private final LikeHistoryRepository likeHistoryRepository;
    private final ProductViewService productViewService;

    @Override
    public boolean isSupport(Boolean like) {
        return like == null;
    }

    @Override
    public List<ProductResponse> getProductList(Long memberId, Pageable pageable) {
        List<ProductResponse> products = productRepository.findAll(pageable).stream()
                .map(product -> new ProductResponse(product
                        , likeHistoryRepository.getTotalLike(product.getId())
                        , productViewService.getViewCount(product.getId())))
                .collect(Collectors.toList());

        products.stream()
                .forEach(productResponse -> productResponse.setLiked(
                        likeHistoryRepository.existsByMemberIdAndProductId(memberId, productResponse.getId())));

        return products;
    }
}

