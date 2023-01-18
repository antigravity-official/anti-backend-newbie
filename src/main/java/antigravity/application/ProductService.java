package antigravity.application;

import antigravity.application.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Page<ProductResponse> searchProducts(Long loginId, Pageable pageable, Optional<Boolean> liked);
}
