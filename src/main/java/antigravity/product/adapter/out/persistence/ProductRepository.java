package antigravity.product.adapter.out.persistence;

import antigravity.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {

}
