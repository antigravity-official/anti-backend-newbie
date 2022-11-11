package antigravity.repository;

import antigravity.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    Optional<WishList> findByUserIdAndProductId(Long id, Long id1);

    List<WishList> findByUserId(Long id);
}
