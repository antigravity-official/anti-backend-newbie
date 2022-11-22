package antigravity.service;

import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.ProductStatistics;
import antigravity.entity.User;
import antigravity.repository.LikedProductRpository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LikedProductRpository likedProductRpository;

    /**
     * 좋아요 상품 등록
     *
     * @param productId 상품 아이디
     * @param userId    사용자 아이디
     * @throws IllegalArgumentException 사용자, 상품 없거나 이미 찜한 상품이면 익셉션 발생
     */
    @Transactional
    public void registerLikeProduct(Long productId, Long userId) throws IllegalArgumentException {
        User foundUser = userRepository.findById(userId);
        if (foundUser == null) {
            throw new IllegalArgumentException("The User dose not exist.");
        }

        Product foundProduct = productRepository.findById(productId);
        if (foundProduct == null) {
            throw new IllegalArgumentException("The product dose not exist.");
        }

        if (isAlreadyLikedProduct(productId, userId)) {
            throw new IllegalArgumentException("This product is already liked Product.");
        }

        LikedProduct likedProduct = LikedProduct.createLikedProduct(foundProduct, foundUser);
        likedProductRpository.save(likedProduct);
    }


    public List<Product> findByLikedStatus(Boolean like, Integer page, Integer size) {
        if (like == null) {

        }
        return null;
    }

    /**
     * 사용자가 이미 제품을 찜 했는지 체크한다.
     *
     * @param productId 상품 아이디
     * @param userId    시용자 아이디
     * @return 찜 여부
     */
    public boolean isAlreadyLikedProduct(Long productId, Long userId) {
        boolean isAlreadyLikedProduct = false;
        List<LikedProduct> byUserIdAndProductId
                = likedProductRpository.findByUserIdAndProductId(userId, productId);

        if (byUserIdAndProductId != null && byUserIdAndProductId.size() != 0) {
            isAlreadyLikedProduct = true;
        }

        return isAlreadyLikedProduct;
    }
}
