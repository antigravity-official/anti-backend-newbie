package antigravity.application.impl;

import antigravity.application.ProductLikeService;
import antigravity.application.ProductViewCacheManager;
import antigravity.application.dto.ProductLikeResponse;
import antigravity.common.exception.NotFoundProductException;
import antigravity.common.exception.NotFoundUserException;
import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;
import antigravity.domain.repository.ProductLikeRepository;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
   private final ProductViewCacheManager productViewCacheManager;

    @Override
    @Transactional
    public ProductLikeResponse like(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::new);

        ProductLike productLike = upsert(product, user);
        productViewCacheManager.incrementProductViewCount(productId);

        return new ProductLikeResponse(productLike.getId(), product.getId());
    }

    private ProductLike upsert(Product product, User user) {
        Optional<ProductLike> optionalLike = productLikeRepository.findByProductAndUser(product, user);

        if (optionalLike.isPresent()) {
            ProductLike productLike = optionalLike.get();
            productLike.recoverLiked();
            return productLike;
        }
        return productLikeRepository.save(ProductLike.of(product, user));
    }
}
