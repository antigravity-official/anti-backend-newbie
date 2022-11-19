package antigravity.repository.custom;

import antigravity.entity.Product;
import antigravity.entity.User;
import com.querydsl.core.QueryResults;

import java.util.List;

public interface ProductLikeRepositoryCustom  {
    List<Product> likeGet(Boolean liked, Long userId);
}
