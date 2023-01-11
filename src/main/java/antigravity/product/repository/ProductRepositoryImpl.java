package antigravity.product.repository;

import antigravity.product.application.ProductRepository;
import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductLikeJpaRepository productLikeJpaRepository;
    private final ProductViewJpaRepository productViewJpaRepository;

    @Override
    public Product getById(Long productId) {
        return productJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Transactional
    @Override
    public void increseViewsByProductId(Long productId) {
        ProductView productView = ProductView.builder()
                .productId(productId)
                .build();
        productViewJpaRepository.save(productView);
    }

    @Transactional
    @Override
    public void like(Long productId, Long userId) {
        // 조회수 증가 해야 함.
        checkAlreadyLikedProduct(productId, userId);
        ProductLike productLike = ProductLike.builder()
                .productId(productId)
                .userId(userId)
                .build();
        productLikeJpaRepository.save(productLike);
    }

    private void checkAlreadyLikedProduct(Long productId, Long userId) {
        if (productLikeJpaRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new IllegalStateException("이미 찜한 상품입니다.");
        }
    }


}
