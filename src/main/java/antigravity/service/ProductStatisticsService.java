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

    /**
     * 조회 수를 증가한다.
     * @param productId 상품아이디
     */
    @Transactional
    public void increaseViewCount(Long productId) {
        Product foundProduct = productRepository.findById(productId);
        if (foundProduct == null) {
            // TODO: product_not_found 전용 익셉션을 만들면 간단함.
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
