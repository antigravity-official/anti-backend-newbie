package antigravity.repository;

import antigravity.entity.GetLikedProductByUserParam;
import antigravity.entity.LikeProduct;
import antigravity.entity.ProductViewCount;
import antigravity.payload.ProductResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikedProductRepository {
     void insertLikedProduct(LikeProduct likeProduct);
     void updateLikedCount(ProductViewCount productViewCount);
     void updateStatusByProductIdAndUserId(LikeProduct likeProduct);
     LikeProduct selectLikedProductByProductIdAndUserId (LikeProduct likeProduct);
     List<ProductResponse> getLikedProductsByUser(GetLikedProductByUserParam param);

     Integer getCountLikedProductByProductId(Long productId);
}
