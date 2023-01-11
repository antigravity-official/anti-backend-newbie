package antigravity.service;

import antigravity.entity.ProductViewCount;
import antigravity.repository.ProductViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductViewService {
    private final ProductViewCountRepository productViewCountRepository;

    public void addCountOne(Long productId) {
        if (!productViewCountRepository.existsById(productId)) {
            productViewCountRepository.save(ProductViewCount.builder().id(productId).build());
        }
        ProductViewCount productViewCount = productViewCountRepository.findById(productId).get();
        productViewCount.setCount(productViewCount.getCount() + 1);
    }

    public Long getViewCount(Long productId) {
        return productViewCountRepository.findById(productId)
                .orElseGet(() -> ProductViewCount.builder().build())
                .getCount();
    }
}
