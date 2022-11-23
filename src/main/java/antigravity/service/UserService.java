package antigravity.service;

import antigravity.entity.Customer;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public Customer findById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * 해당상품이 좋아요가 됐는지 체크한다.
     * @param userId 사용자 아이디
     * @param productId 상품 아이디
     * @return 좋아요 상품 등록 여부
     */
    public boolean isLikeProduct(Long userId, Long productId) {
        Customer foundCustomer = findById(userId);

        long count = foundCustomer.getLikedProducts()
                .stream()
                .filter(i -> Objects.equals(i.getProduct().getId(), productId))
                .count();

        return count > 0;
    }
}
