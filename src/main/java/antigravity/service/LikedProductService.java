package antigravity.service;

import antigravity.entity.*;
import antigravity.exception.AlreadyExistLikedProductException;
import antigravity.exception.ProductNotFoundException;
import antigravity.exception.UserNotFoundException;
import antigravity.payload.ProductResponse;
import antigravity.repository.LikedProductRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LikedProductService {
    private final LikedProductRepository likedProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createLikedProduct(Long userId, Long productId) {

        /**
         * 1. product 조회 후 없으면 throw
         *
         * 2. user 조회 후 없으면 throw
         *
         * 3. 찜 조회 후 있으면 -> liked_status 확인
         * 3.1. liked_status -> false -> true 로 update
         * 3.2. liked_status -> true -> throw
         * 3.3 없으면 -> insert
         *
         * 4. 상품 조회수 update
         *  - insert or update
         */

        LikeProduct likeProduct = LikeProduct.builder()
                .userId(userId)
                .productId(productId)
                .likeStatus(true)
                .build();

        productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product is not Found."));
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User is not exist."));

        LikeProduct getLikeProduct = likedProductRepository.selectLikedProductByProductIdAndUserId(likeProduct);

        if (getLikeProduct == null) {
            likedProductRepository.insertLikedProduct(
                    LikeProduct.builder()
                            .userId(userId)
                            .productId(productId)
                            .likeStatus(true)
                            .build());
        } else {
            if (getLikeProduct.isLikeStatus()) {
                throw new AlreadyExistLikedProductException("Already exist Liked Product.");
            }
            likedProductRepository.updateStatusByProductIdAndUserId(likeProduct);
        }

        likedProductRepository.updateLikedCount(
                ProductViewCount.builder()
                        .productId(productId)
                        .build()
        );
    }


    @Transactional(readOnly = true)
    public List<ProductResponse> getLikedProductsByUser(Integer userId, Boolean liked, Integer page, Integer size) {
        List<ProductResponse> list = likedProductRepository.getLikedProductsByUser(GetLikedProductByUserParam.builder()
                .liked(liked)
                .page(page)
                .size(size)
                .userId(userId).build());

        list.forEach(a -> a.setTotalLiked(likedProductRepository.getCountLikedProductByProductId(a.getId())));

        return list;
    }
}
