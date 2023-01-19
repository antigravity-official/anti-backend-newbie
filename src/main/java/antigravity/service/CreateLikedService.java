package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.repository.ProductLikeRepository;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateLikedService {
    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;

    private final SearchUserService searchUserService;
    private final SearchProductService searchProductService;

    @Transactional
    public void createLiked(Long userId, Long productId) {
       User user = searchUserService.searchUserByUserId(userId);
       Product product = searchProductService.searchProductByProductId(productId);

       Boolean isExistsLiked = productLikeRepository.existsByUserAndProduct(user, product);

       if (isExistsLiked) {
           throw new CustomException(ErrorCode.CAN_NOT_LIKED);
       }

       ProductLike productLike = new ProductLike(user, product);
       productLikeRepository.save(productLike);
       productRepository.increaseTotalLiked(productId);
    }

}
