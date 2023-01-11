package antigravity.api.repository;

import antigravity.entity.LikeStatus;
import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    @Query("select distinct pl from ProductLike pl left join pl.product left join fetch pl.user " +
            "where pl.user.id = :userId and pl.likeStatus = :likedStatus")
    List<ProductLike> findProductLikeByUserAndLikeStatus(@Param("userId") Long userId,
                                                         @Param("likedStatus") LikeStatus likedStatus,
                                                         Pageable pageable);


    /**
     * 단지 해당 ProductLike 에서 Product를 참조 안하기 때문에, N+1 발생 안하기 때문에 fetch join x
     * @param user
     * @param product
     * @return Boolean -> 존재하면 True
     */
    Boolean existsByUserAndProduct(User user, Product product);
}
