package antigravity.application;

import antigravity.application.dto.ProductRegisterResponse;
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
   //private final ProductViewCacheManager productViewCacheManager;

    @Override
    @Transactional
    public ProductRegisterResponse like(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(RuntimeException::new);

        upsert(product, user);
        //productViewCacheManager.incrementProductViewCount(productId);

        return new ProductRegisterResponse(product.getId(), product.getSku(), product.getName());
    }

    private void upsert(Product product, User user) {
        Optional<ProductLike> optionalLike = productLikeRepository.findByProductAndUser(product, user);

        if (optionalLike.isPresent()) {
            ProductLike productLike = optionalLike.get();
            productLike.recoverLiked();
            return;
        }
        productLikeRepository.save(ProductLike.of(product, user));
    }
}
