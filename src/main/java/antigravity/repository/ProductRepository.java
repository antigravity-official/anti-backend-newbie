package antigravity.repository;


import antigravity.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Modifying
  @Query("update Product p set p.viewed = p.viewed + 1 where p.id = :productId")    int updateView(Long productId);



}
