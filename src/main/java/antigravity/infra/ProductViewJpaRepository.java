package antigravity.infra;

import antigravity.domain.ProductView;
import antigravity.domain.repository.ProductViewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductViewJpaRepository extends JpaRepository<ProductView, Long>, ProductViewRepository {

    @Override
    @Query("select pv.viewCount from ProductView pv where pv.productId = :productId")
    Long findCountById(@Param("productId") Long productId);

    @Query("select pv.productId from ProductView pv")
    List<Long> getProductIds();

    @Override
    @Query("update ProductView pv set pv.viewCount = :viewCount where pv.productId = :productId")
    void updateViewCount(@Param("productId") Long productId, @Param("viewCount") Long viewCount);
}
