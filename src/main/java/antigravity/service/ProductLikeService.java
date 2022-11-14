package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.exception.ProductCommonException;
import antigravity.exception.UserCommonException;
import antigravity.repository.ProductLikeRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductLikeService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;
    private final ProductHitsService productHitsService;

    @Transactional
    public void likedProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserCommonException("[INVALID] User not found, try again"));
        Product likedTargetProduct = productRepository.findById(productId).orElseThrow(()
                        -> new ProductCommonException("[INVALID] Product not found, try again"));
        boolean canLikedProduct = productLikeRepository.existsByUserAndProduct(user, likedTargetProduct);
        if( canLikedProduct ) {
            throw new ProductCommonException("This product has already been requested");
        }
        ProductLike savedProductLike = productLikeRepository.save(ProductLike.newInstance(user, likedTargetProduct));
        productHitsService.increaseHits(productId);
        log.info(String.format("Success liked target product - %d", savedProductLike.getId()));
    }
}
