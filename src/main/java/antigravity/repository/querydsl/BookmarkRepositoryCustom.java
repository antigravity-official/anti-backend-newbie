package antigravity.repository.querydsl;

import antigravity.payload.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkRepositoryCustom {
    Page<ProductResponse> findByLikedAndUserId(Boolean liked, Pageable pageable, Long userId);
}