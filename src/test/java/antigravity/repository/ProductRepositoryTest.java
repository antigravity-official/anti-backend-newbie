package antigravity.repository;

import antigravity.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @Transactional
    @DisplayName("상품 등록")
    void postProduct() {
        //given
        Product product = new Product();
        product.setSku("G2000000523");
        product.setName("소울 블랙 브라");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setQuantity(10);

        //when
        Product saveProduct = productRepository.save(product);
        Long savedId = saveProduct.getId();
        Optional<Product> findProduct = productRepository.findById(savedId);

        //then
        Assertions.assertThat(findProduct.get().getId()).isEqualTo(product.getId());
        Assertions.assertThat(findProduct.get().getName()).isEqualTo(product.getName());
    }

    @Test
    @Transactional
    @DisplayName("개별 상품 조회 시 viewed 컬럼 데이터가 증가 한다.")
    @Rollback(false)
    void productViewIncrease() {
        //given
        List<Product> products = productRepository.findAll();
        Product product = products.get(0);
        //when
        Optional<Product> findProduct = productRepository.findById(product.getId());
        findProduct.ifPresent(Product::productViewIncrease);

        //then
        Assertions.assertThat(findProduct.get().getViewed()).isEqualTo(2);
    }

}