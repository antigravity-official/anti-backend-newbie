package antigravity.product.service.impl;

import antigravity.product.domain.dao.LikeProductDAO;
import antigravity.product.domain.dao.ProductDAO;
import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.service.LikeProductService;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeProductServiceImpl implements LikeProductService {
    private final LikeProductDAO likeProductDAO;
    private final ViewServiceImpl viewService;
    private final UserService userService;
    private final ProductDAO productDAO;

    @Override
    public Page<ProductResponse> findLikeProduct(Integer userId, Pageable pageable) {
        return likeProductDAO.findAllByUserId(userId,pageable).map(dipProduct ->
                ProductResponse.createDipProduct(productDAO.findById(dipProduct.getProductId()), likeProductDAO.calculateTotalDip(dipProduct.getProductId())));
    }

    @Override
    @Transactional
    public LikeProductResponse createLikeProduct(Integer userId, Long productId) {
        // 유저 존재 X
        userService.validateExistUser(userId);

        // 상품 존재 X
        productDAO.validateExistProduct(productId);

        // 이미 찜했다면
        likeProductDAO.checkAlreadyDip(userId, productId);

        // 조회수 처리
        Long viewCnt = viewService.addViewCntToRedis(productId);

        // 찜한 상품 저장
        likeProductDAO.saveLikeProduct(LikeProduct.builder()
                .productId(productId)
                .userId(userId).build());

        return LikeProductResponse.builder()
                .userId(userId)
                .productId(productId)
                .viewed(viewCnt)
                .build();
    }

}
