package antigravity.product.application;

import antigravity.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeProductService {
    private final ProductRepository productRepository;

    public void like(Long productId, Long userId) {
        //1. 상품이 유효한지(없는 상품, 삭제된 상품)
        checkProductCanBeLiked(productId);
        //2. 조회 수 증가
        productRepository.increseViewsByProductId(productId);
        //3. 찜
        productRepository.like(productId, userId);
    }

    private void checkProductCanBeLiked(Long productId) {
        Product product = productRepository.getById(productId);
        if (product.isDeleted()) {
            throw new IllegalArgumentException("삭제된 상품입니다.");
        }
    }
}
