package antigravity.application.impl;

import antigravity.application.ProductService;
import antigravity.application.dto.ProductResponse;
import antigravity.common.exception.NotFoundUserException;
import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;
import antigravity.domain.repository.ProductLikeRepository;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;
    private final UserRepository userRepository;


    @Override
    public Page<ProductResponse> searchProducts(Long loginId, Pageable pageable, Optional<Boolean> liked) {
        User user = userRepository.findById(loginId)
                .orElseThrow(NotFoundUserException::new);

        Page<Product> products = getProducts(user, pageable, liked);

        return liked.map(likedCondition -> productToPageResponseWithLikeCondition(products, likedCondition))
                .orElseGet(() -> anyProductToResponse(user, products));
    }


    private Page<Product> getProducts(User user, Pageable pageable, Optional<Boolean> liked) {
        if (liked.isPresent()) {

            if (liked.get()) {
                return productRepository.findLikedProducts(user, pageable);
            }
            return productRepository.findNotLikedProducts(user, pageable);

        }
        return productRepository.findAnyProducts(pageable);
    }

    private Page<ProductResponse> productToPageResponseWithLikeCondition(Page<Product> products, Boolean likeCondition) {
        // mock viewCount
        final Integer viewCount = 1;

        return products.map(p -> {
            final Integer totalLiked = productLikeRepository.countByProduct(p);
            return ProductResponse.of(p, likeCondition, totalLiked, viewCount);
        });
    }

    private Page<ProductResponse> anyProductToResponse(User user, Page<Product> products) {
        // mock viewCount
        final Integer viewCount = 1;

        Set<ProductLike> productLikes = productLikeRepository.findProductLikeByUserAndProducts(user, products.getContent());
        return products.map(p -> {
            final Integer totalLiked = productLikeRepository.countByProduct(p);
            return ProductResponse.of(p, productLikes.contains(p), totalLiked, viewCount);
        });

    }

}
