package antigravity.repository;

import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositoryCustom  {
    Page<ProductResponse> findAllProduct(Long userId, Pageable pageable);

    Page<ProductResponse> findProductTrueLike(Long userId, Pageable pageable, boolean wishBit);
    Page<ProductResponse> findProductFalseLike(Long userId, Pageable pageable);
}
