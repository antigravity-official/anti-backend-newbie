package antigravity.repository;

import antigravity.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id not in (select p.id from Product p inner join LikeHistory h on p = h.product where h.member.id = :memberId)")
    List<Product> getNotLikeProduct(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select p from Product p inner join LikeHistory h on p = h.product where h.member.id = :memberId")
    List<Product> getLikeProduct(@Param("memberId") Long memberId, Pageable pageable);

}
