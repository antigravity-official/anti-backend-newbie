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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        productRepository.findById(productId).orElseThrow(() -> new AntiException(ProductErrorCode.PRODUCT_NOT_EXIST));

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
    public Page<ProductResponse> findProductList(Integer userId, Boolean liked, Pageable pageable) {
        // 모든 상품을 조회
        if (liked == null) {
            Page<Product> products = productRepository.findAll(pageable);
            Map<Long, ProductResponse> productMap = new HashMap<>();

            //어자피 len(Product) >= len(dipProduct) 이므로 pageable 똑같이 써도 상관X
            dipProductRepository.findAllByUserId(userId, pageable)
                    .map(dipProduct -> ProductResponse.createDipProduct(productRepository.findById(dipProduct.getProductId()).get(), dipProductRepository.countDipProductByProductId(dipProduct.getProductId())))
                    .forEach(productResponse -> productMap.put(productResponse.getId(), productResponse));

            return products.map(product -> productMap.getOrDefault(product.getId(), ProductResponse.createNotDipProduct(product,dipProductRepository.countDipProductByProductId(product.getId()))));
        } else if (liked == true) { // 찜한 상품을 조회
            return dipProductRepository.findAllByUserId(userId, pageable)
                    .map(dipProduct -> ProductResponse.createDipProduct(productRepository.findById(dipProduct.getProductId()).get(), dipProductRepository.countDipProductByProductId(dipProduct.getProductId())));
        } else { // 찜하지 않은 상품만 조회
            Page<DipProduct> dipProducts = dipProductRepository.findAllByUserId(userId, pageable);
            List<Long> dipProductIds = dipProducts.stream().map(DipProduct::getProductId).collect(Collectors.toList());
            return productRepository.findAllNotDipProduct(dipProductIds, pageable)
                    .map(notDipProduct -> ProductResponse.createNotDipProduct(notDipProduct, dipProductRepository.countDipProductByProductId(notDipProduct.getId())));

        }
    }
}
