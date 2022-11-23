package antigravity.service;

import antigravity.entity.Customer;
import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.repository.LikedProductRpository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
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
        Customer foundCustomer = userRepository.findById(userId);
        if (foundCustomer == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Product foundProduct = productRepository.findById(productId);
        if (foundProduct == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        if (isAlreadyLikedProduct(productId, userId)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        LikedProduct likedProduct = LikedProduct.createLikedProduct(foundProduct, foundCustomer);
        likedProductRpository.save(likedProduct);
    }

    /**
     * LIKE 에 따라서 조회 조건 분기 하여 상품목록을 가져온다.
     * @param userId 사용자 아이디
     * @param like 좋아요 여부
     * @param page 조회 offset
     * @param size 조회 limit
     * @return
     */
    public List<Product> findByLikedStatus(Boolean like, Long userId, Integer page, Integer size) {
        List<Product> findList = null;
        if (like == null) {
            findList = productRepository.findAll(page, size);
        } else if (like) {
            findList = productRepository.findLikedProduct(userId, page, size);
        } else {
            findList = productRepository.findNotLikedProduct(userId, page, size);
        }
        return findList;
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
