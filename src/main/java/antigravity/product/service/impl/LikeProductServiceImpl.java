package antigravity.product.service.impl;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.repository.LikeProductRepository;
import antigravity.product.exception.ProductErrorCode;
import antigravity.product.service.LikeProductService;
import antigravity.product.service.ProductService;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeProductServiceImpl implements LikeProductService {
    private final LikeProductRepository likeProductRepository;
    private final ViewServiceImpl viewService;
    private final UserService userService;
    private final ProductService productService;
    @Override
    @Transactional
    public LikeProductResponse createDip(Integer userId, Long productId) {
        // 유저 존재 X
        userService.validateExistUser(userId);

        // 상품 존재 X
        productService.validateExistProduct(productId);

        // 이미 찜했다면
        checkAlreadyDip(userId, productId);

        // 조회수 처리
        Long viewCnt = viewService.addViewCntToRedis(productId);

        // 찜한 상품 저장
        likeProductRepository.save(LikeProduct.builder()
                .productId(productId)
                .userId(userId).build());

        return LikeProductResponse.builder()
                .userId(userId)
                .productId(productId)
                .viewed(viewCnt)
                .build();
    }

    @Override
    public void checkAlreadyDip(Integer userId, Long productId) {
        if(likeProductRepository.existsDipProductByUserIdAndProductId(userId, productId) > 0) {
            throw new AntiException(ProductErrorCode.PRODUCT_ALREADY_LIKE);
        }
    }
}
