package antigravity.repository;

import antigravity.entity.Product;
import antigravity.repository.custom.CustomProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product,Long>{

	Page<Product> findAllByDeletedAtIsNull(Pageable pageable);
}
