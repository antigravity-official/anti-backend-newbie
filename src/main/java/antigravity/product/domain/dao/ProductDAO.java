package antigravity.product.domain.dao;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.exception.ProductErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDAO {
    private final ProductRepository productRepository;
    public void validateExistProduct(Long productId) {
        productRepository.findById(productId).orElseThrow(() -> new AntiException(ProductErrorCode.PRODUCT_NOT_EXIST));
    }
    public Page<Product> findAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new AntiException(ProductErrorCode.PRODUCT_NOT_EXIST));}
    public Page<Product> findAllNotDipProduct(List<Long> dipProductIds, Pageable pageable) {
        return productRepository.findAllNotDipProduct(dipProductIds, pageable);
    }

}
