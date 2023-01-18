package antigravity.application;

import antigravity.application.dto.ProductResponse;
import antigravity.common.exception.NotFoundUserException;
import antigravity.domain.Product;
import antigravity.domain.User;
import antigravity.domain.repository.ProductLikeRepository;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;
    private final UserRepository userRepository;


    @Override
    public Page<ProductResponse> searchProducts(Long loginId, Pageable pageable, Optional<Boolean> liked) {
        User user = userRepository.findById(loginId)
                .orElseThrow(NotFoundUserException::new);

        if (liked.isPresent()) {
            Boolean likedCondition = liked.get();

            if (likedCondition) {
                Page<Product> likedProducts = productRepository.findLikedProducts(user, pageable);
            } else {
                Page<Product> notLikedProducts = productRepository.findNotLikedProducts(user, pageable);
            }
        } else {
            Page<Product> anyProducts = productRepository.findAnyProducts(pageable);
        }

        return null;
    }


    private Page<ProductResponse>ProductToPageResponse(User user, Page<Product> products) {

        return null;
    }
}
