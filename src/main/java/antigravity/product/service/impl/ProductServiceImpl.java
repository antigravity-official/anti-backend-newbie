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
    @Override
    public DipProductResponse createDip(Integer userId, Long productId) {
        userRepository.findById((long)userId).orElseThrow(() -> new AntiException(UserErrorCode.USER_NOT_EXIST));
        Product product = productRepository.findById(productId).orElseThrow(() -> new AntiException(ProductErrorCode.PRODUCT_NOT_EXIST));

        //이미 찜했다면
        if (dipProductRepository.existsDipProductByUserIdAndProductId((long)userId, productId)) {
            throw new AntiException(ProductErrorCode.PRODUCT_ALREADY_DIP);
        }

        dipProductRepository.save(DipProduct.builder()
                .product(product)
                .userId(userId).build());
        return DipProductResponse.builder()
                .userId(userId)
                .productId(productId)
                .build();
    }

    @Override
    public List<ProductResponse> findProductList(Integer userId, boolean liked, Pageable pageable) {
        return null;
    }
}
