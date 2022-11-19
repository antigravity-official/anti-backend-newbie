package antigravity.service;

import antigravity.dto.RequestDto.LikePostRequestDto;
import antigravity.entity.LikeProduct;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.dto.payload.ProductResponse;
import antigravity.repository.LikeProductRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private LikeProductRepository likeProductRepository;

    public ProductService(ProductRepository productRepository,
                          UserRepository userRepository,
                          LikeProductRepository likeProductRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.likeProductRepository = likeProductRepository;
    }


    public List<ProductResponse> findProducts() {
        List<Product> all = productRepository.findAll();
        List<ProductResponse> result = new ArrayList<>();
        for (Product product : all) {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.getId())
                    .liked(null)
                    .totalLiked(null)
                    .createdAt(product.getCreatedAt())
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .sku(product.getSku())
                    .viewed(product.getView() + 1)
                    .build();
            result.add(productResponse);
        }
        return result;
    }

    @Transactional
    public String likePost(Long productId, Long userId) {
        Optional<User> userTarget = userRepository.findById(userId);
        Optional<Product> productTarget = productRepository.findById(productId);
        LikeProduct target = likeProductRepository.findByUserId(userId);

//        유저 존재여부 검증
        if (userTarget.isEmpty() || userTarget.get().getDeletedAt() != null) {
            return "존재하지 않는 유저입니다.";
        }
//        제품 존재여부 검증
        if (productTarget.isEmpty() || productTarget.get().getDeletedAt() != null) {
            return "존재하지 않는 제품입니다.";
        }
//        이미 찜했는지 검증
        if (target != null) {
            if (target.getProductId().equals(productId)) {
                return "이미 찜한 제품입니다.";
            }
        }
//        조회수 증가
        int viewPoint = productTarget.get().getView();
        productTarget.get().viewCount(viewPoint + 1);

        LikeProduct likeProduct = new LikeProduct(userId, productId);
        likeProductRepository.save(likeProduct);
        return "success!";
    }
}