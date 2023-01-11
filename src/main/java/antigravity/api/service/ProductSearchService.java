package antigravity.api.service;

import antigravity.api.repository.ProductLikeRepository;
import antigravity.api.repository.ProductRepository;
import antigravity.api.repository.UserRepository;
import antigravity.entity.*;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.payload.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public List<ProductSearchResponse> searchLikedProduct(Long userId, Boolean isLiked, Pageable pageable) {

        User findUser = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        if (Boolean.TRUE.equals(isLiked)){
            return takeLikedProductWithUserId(userId, true, pageable);
        } else if (Boolean.FALSE.equals(isLiked)){
            return takeNoLikedProductWithUserId(findUser, false, pageable);
        } else{
            return takeProductAll(findUser,pageable);
        }

    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeNoLikedProductWithUserId(User user, boolean isLiked, Pageable pageable) {
        return productRepository.findProductsNotInProductLike(user)
                .stream()
                .map(product -> makeProductSearchResponseWithProduct(
                        product, isLiked, product.getProductLikes().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeLikedProductWithUserId(Long userId, boolean isLiked, Pageable pageable) {
        return productLikeRepository.findProductLikeByUserAndLikeStatus(userId, LikeStatus.LIKE)
                .stream()
                .map(productLike -> makeProductSearchResponseWithProduct(
                        productLike.getProduct(), isLiked, productLike.getProduct().getProductLikes().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeProductAll(User user, Pageable pageable) {
        List<ProductSearchResponse> responseList = new ArrayList<>();

        for (Product product : productRepository.findAllProduct()) {
            boolean flag = false;

            for (ProductLike productLike : product.getProductLikes()) {
                if (productLike.getUser().equals(user)){
                    responseList.add(makeProductSearchResponseWithProduct(product,true,product.getProductLikes().size()));
                    flag = true;
                }
            }
            if (!flag){
                responseList.add(makeProductSearchResponseWithProduct(product,false,product.getProductLikes().size()));
            }
        }
        return responseList;
    }

    private ProductSearchResponse makeProductSearchResponseWithProduct(Product product, Boolean isLiked, Integer totalLiked) {
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