package antigravity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
