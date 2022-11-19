package antigravity.service;

import antigravity.entity.LikeProduct;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.dto.payload.ProductResponse;
import antigravity.repository.LikeProductRepository;
import antigravity.repository.custom.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
        LikeProduct target = likeProductRepository.findByUserId(userId).orElse(null);

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

    public List<ProductResponse> likeGet(Boolean liked, Long userId,Pageable pageable) {

        List<Product> products = productRepository.likeGet(liked, userId, pageable);
        Optional<LikeProduct> targets = likeProductRepository.findByUserId(userId);
        Optional<User> userTarget = userRepository.findById(userId);
        if (userTarget.isEmpty()) {
            return null;
        }

        Long targetId = null;
        if (targets.isPresent()) {
         targetId = targets.get().getProductId();
        }

        List<ProductResponse> response = new ArrayList<>();
        int totalLiked = products.size();

        ProductResponse productResponse = ProductResponse.builder().build();
//        liked 비어있는 경우
        if (liked == null) {
            for (Product product : products) {
//                찜 상태인 제품인 경우
                if (product.getId().equals(targetId)) {
                    productResponse = ProductResponse.builder()
                            .id(product.getId())
                            .sku(product.getSku())
                            .name(product.getName())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .liked(true)
                            .totalLiked(totalLiked)
                            .viewed(product.getView())
                            .createdAt(product.getCreatedAt())
                            .updatedAt(product.getUpdatedAt())
                            .build();
                    response.add(productResponse);
                } else {
//                    찜 상태인 제품이 아닌 경우
                    productResponse = ProductResponse.builder()
                            .id(product.getId())
                            .sku(product.getSku())
                            .name(product.getName())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .liked(false)
                            .totalLiked(totalLiked)
                            .viewed(product.getView())
                            .createdAt(product.getCreatedAt())
                            .updatedAt(product.getUpdatedAt())
                            .build();
                    response.add(productResponse);
                }
            }
        } else {
            for (Product product : products) {
                productResponse = ProductResponse.builder()
                        .id(product.getId())
                        .sku(product.getSku())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .totalLiked(totalLiked)
                        .viewed(product.getView())
                        .createdAt(product.getCreatedAt())
                        .updatedAt(product.getUpdatedAt())
                        .build();
                response.add(productResponse);
            }
        }

        return response;
    }
}
