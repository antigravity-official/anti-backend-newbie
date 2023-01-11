package antigravity.api.service;

import antigravity.api.repository.ProductLikeRepository;
import antigravity.api.repository.ProductRepository;
import antigravity.entity.LikeStatus;
import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;

    private final UserSearchService userSearchService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void addLikedProduct(Long userId, Long productId) {
        User findUser = userSearchService.searchUserByUserId(userId);

        Product findProduct = searchProductByProductId(productId);

        Boolean isLikedProduct = productLikeRepository.existsByUserAndProduct(findUser, findProduct);

        if (isLikedProduct)
            throw new CustomException(ErrorCode.CAN_NOT_LIKE);

        ProductLike createProductLike = createProductLikeUser(findUser, findProduct);
        productLikeRepository.save(createProductLike);
    }

    @Transactional(readOnly = true)
    public Product searchProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
                });
    }

    private ProductLike createProductLikeUser(User user, Product product) {
        return ProductLike.builder()
                .user(user)
                .product(product)
                .likeStatus(LikeStatus.LIKE)
                .build();
    }
}
