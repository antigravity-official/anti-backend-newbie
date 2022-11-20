package antigravity.domain.repository;

import antigravity.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface ProductRepositoryCustom {

    Page<ProductResponseDto> getWantProducts(Boolean liked, Long userId, Pageable pageable);
}
