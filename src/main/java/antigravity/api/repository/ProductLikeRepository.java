package antigravity.api.repository;

import antigravity.entity.LikeStatus;
import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    @Query("select distinct pl from ProductLike pl LEFT JOIN pl.product LEFT JOIN FETCH pl.user " +
            "where pl.user.id = :userId and pl.likeStatus = :likedStatus")
    List<ProductLike> findProductLikeByUserAndLikeStatus(@Param("userId") Long userId,
                                                         @Param("likedStatus") LikeStatus likedStatus);

    /**
     * 단지 해당 ProductLike에서 Product를 참조 안하기 때문에 영속성 컨텍스트 안건드린다.
     * 따라서, N+1 발생 안하기 때문에 fetch join x
     *
     * @param user
     * @param product
     * @return Boolean -> 존재하면 True
     */
    Boolean existsByUserAndProduct(User user, Product product);
}
