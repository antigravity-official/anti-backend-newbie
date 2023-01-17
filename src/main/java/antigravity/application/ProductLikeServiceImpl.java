package antigravity.application;

import antigravity.application.dto.ProductRegisterResponse;
import antigravity.domain.repository.ProductLikeRepository;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService{


    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ProductRegisterResponse like(Long userId, Long productId) {
        return null;
    }
}
