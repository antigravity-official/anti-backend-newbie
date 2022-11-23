package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.ProductStatistics;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.repository.ProductRepository;
import antigravity.repository.ProductStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductStatisticsService {
    private final ProductStatisticsRepository productStatisticsRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void increaseViewCount(Long productId) {
        Product foundProduct = productRepository.findById(productId);
        if (foundProduct == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        ProductStatistics foundProductStatistics = foundProduct.getProductStatistics();
        if (foundProductStatistics != null) {
            foundProductStatistics.increaseViewCount();
        } else {
            ProductStatistics productStatistics = foundProduct.createProductStatistics();
            productStatisticsRepository.save(productStatistics);
        }
    }
}