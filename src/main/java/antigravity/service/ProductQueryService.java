package antigravity.service;

import antigravity.entity.Product;
import antigravity.exception.NotFoundProductException;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductQueryService {
    private final ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundProductException());
    }
}
