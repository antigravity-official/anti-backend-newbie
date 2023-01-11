package antigravity.repository;

import antigravity.entity.GetLikedProductByUserParam;
import antigravity.entity.LikeProduct;
import antigravity.entity.Product;
import antigravity.entity.ProductViewCount;
import antigravity.payload.ProductResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductRepository {
     Optional<Product> findById(Long id);

}
