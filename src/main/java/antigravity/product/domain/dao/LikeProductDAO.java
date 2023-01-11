package antigravity.product.domain.dao;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.repository.LikeProductRepository;
import antigravity.product.exception.ProductErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeProductDAO {
    private final LikeProductRepository likeProductRepository;
    public int calculateTotalDip(Long productId) {
        return likeProductRepository.countDipProductByProductId(productId);
    }

    public void checkAlreadyDip(Integer userId, Long productId) {
        if(likeProductRepository.existsDipProductByUserIdAndProductId(userId, productId) > 0) {
            throw new AntiException(ProductErrorCode.PRODUCT_ALREADY_LIKE);
        }
    }

    public Page<LikeProduct> findAllByUserId(Integer userId, Pageable pageable) {
        return likeProductRepository.findAllByUserId(userId, pageable);
    }

    public Long saveLikeProduct(LikeProduct likeProduct) {
        return likeProductRepository.save(likeProduct);
    }
}
