package antigravity.product.service.impl;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.entity.DipProduct;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.DipProductRepository;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.exception.ProductErrorCode;
import antigravity.product.service.ProductService;
import antigravity.product.web.dto.DipProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.user.exception.UserErrorCode;
import antigravity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private DipProductRepository dipProductRepository;
    private UserRepository userRepository;
    private ViewServiceImpl viewService;
    @Override
    public DipProductResponse createDip(Integer userId, Long productId) {
        // 유저 존재 X
        userRepository.findById((long)userId).orElseThrow(() -> new AntiException(UserErrorCode.USER_NOT_EXIST));

        // 상품 존재 X
        Product product = productRepository.findById(productId).orElseThrow(() -> new AntiException(ProductErrorCode.PRODUCT_NOT_EXIST));

        // 이미 찜했다면
        if (dipProductRepository.existsDipProductByUserIdAndProductId(userId, productId) > 0) {
            throw new AntiException(ProductErrorCode.PRODUCT_ALREADY_DIP);
        }

        // 조회수 처리
        Long viewCnt = viewService.addViewCntToRedis(productId);

        // 찜한 상품 저장
        dipProductRepository.save(DipProduct.builder()
                .productId(productId)
                .userId(userId).build());

        return DipProductResponse.builder()
                .userId(userId)
                .productId(productId)
                .viewed(viewCnt)
                .build();
    }

    @Override
    public List<ProductResponse> findProductList(Integer userId, boolean liked, Pageable pageable) {
        return null;
    }
}
