package antigravity.api.service;

import antigravity.api.repository.ProductLikeRepository;
import antigravity.api.repository.ProductRepository;
import antigravity.api.repository.UserRepository;
import antigravity.entity.LikeStatus;
import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.payload.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public Object searchLikedProduct(Long userId, Boolean isLiked, Pageable pageable) {

        User findUser = userRepository.findById(userId).orElseThrow(() ->{
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });


        // 과제 2-1. liked 파리미터가 없으면 모든 상품을 조히하되 user가 찜한 상품에 liked: true를 추가한다.
//        List<ProductSearchResponse> responseList = takeNoLikedProducts(pageable);

//        // 과제 2-2. liked = false일 때, 찜하지 않은 상품들만 조회한다.
//        List<ProductSearchResponse> responseList2 = takeNoLikedProductWithUserId(userId, pageable);
//
//        // 과제 2-3. liked = true일 때, 모든 찜한 상품을 조회한다. => 완성
//        List<ProductSearchResponse> responseList1 = takeLikedProductWithUserId(userId, pageable);


        return null;
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeNoLikedProducts(Pageable pageable) {
        return productRepository.findAllProduct(pageable)
                .stream()
                .filter(product -> product.getProductLikes().size() == 0)
                .map(product -> makeProductSearchResponseWithProduct(product, true, 0))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeLikedProductWithUserId(Long userId, Pageable pageable) {
        return productLikeRepository.findProductLikeByUserAndLikeStatus(userId, LikeStatus.LIKE, pageable)
                .stream()
                .map(productLike -> makeProductSearchResponseWithProduct(productLike.getProduct(), true, productLike.getProduct().getProductLikes().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeNoLikedProductWithUserId(Long userId, Pageable pageable){
        return productRepository.findProductsNotInProductLike(userId, pageable)
                .stream()
                .map(product -> makeProductSearchResponseWithProduct(product,true,0))
                .collect(Collectors.toList());
    }


    private ProductSearchResponse makeProductSearchResponseWithProduct(Product product, boolean isLiked, Integer totalLiked) {
        return ProductSearchResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .viewed(product.getViewed())
                .createdAt(product.getCreatedAt().toString())
                .updatedAt(product.getUpdatedAt().toString())
                .liked(isLiked)
                .totalLiked(totalLiked)
                .build();
    }
}
