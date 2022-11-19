package antigravity.repository.custom;

import antigravity.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductLikeRepositoryCustom  {
    List<Product> likeGet(Boolean liked, Long userId, Pageable pageable);
}
