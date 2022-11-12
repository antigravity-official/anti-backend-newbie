package antigravity.repository;

import antigravity.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> getPage(List<Long> productIds, Pageable pageable);
}
