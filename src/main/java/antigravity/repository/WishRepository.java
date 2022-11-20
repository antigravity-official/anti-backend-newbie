package antigravity.repository;

import antigravity.entity.Wish;

import java.util.Optional;

public interface WishRepository {
    Optional<Wish> findById(Long userId, Long productId);

    void save(Long userId, Long productId);
}
