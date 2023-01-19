package antigravity.repository;

import antigravity.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long id);

    @Query(value = "SELECT p FROM Product p")
    List<Product> findProduct(Pageable pageable);

    List<Product> findByIdNotIn(List<Long> ids, Pageable pageable);

    List<Product> findByIdIn(List<Long> ids,Pageable pageable);
}
