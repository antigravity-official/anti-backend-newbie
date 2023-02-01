package antigravity.product.repository;

import antigravity.product.application.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final ProductQueryDslRepository productQueryDslRepository;

    @Override
    public SearchResponse<ProductResponse> search(Boolean liked, Long userId, Integer page,
            Integer size) {

        if (liked == null) {
            Page<ProductResponse> productResponses = productQueryDslRepository.searchAll(userId,
                    page, size);
            return new SearchResponse<>(productResponses.getContent(), productResponses.getSize(),
                    productResponses.getPageable().getPageNumber(),
                    productResponses.getTotalElements());
        }
        if (liked.equals(true)) {
            Page<ProductResponse> productResponses = productQueryDslRepository.searchWithLiked(
                    userId, page, size);
            return new SearchResponse<>(productResponses.getContent(), productResponses.getSize(),
                    productResponses.getPageable().getPageNumber(),
                    productResponses.getTotalElements());
        }

        Page<ProductResponse> productResponses = productQueryDslRepository.searchWithDidNotLiked(
                userId, page, size);
        return new SearchResponse<>(productResponses.getContent(), productResponses.getSize(),
                productResponses.getPageable().getPageNumber(),
                productResponses.getTotalElements());
    }
}
