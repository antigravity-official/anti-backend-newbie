package antigravity.api.service;

import antigravity.api.repository.ProductLikeRepository;
import antigravity.api.repository.ProductRepository;
import antigravity.api.repository.UserRepository;
import antigravity.entity.Product;
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
    private final UserRepository userRepository;

    // TODO: 2023-01-10 동시성 이슈를 위해서 Lock 고려, 과제 2 다하고 돌아오자.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void addLikedProduct(Integer userId, Long productId) {

        User findUser = userRepository.findById(userId.longValue())
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
                });

        Boolean isLikedProduct = productLikeRepository.existsByUserAndProduct(findUser, findProduct);

        if (isLikedProduct) {
            throw new CustomException(ErrorCode.CAN_NOT_LIKE);
        } else {
            findProduct.incrementHits();
        }
        productRepository.save(findProduct);
    }
}
