package antigravity.service;

import antigravity.dto.ProductDTO;
import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;


@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired ProductService productService;
    @Autowired ProductRepository productRepository;

    @Test
    public void 상품존재확인() {
        Product product = Product.builder().id(1L).name("name")
                .price(new BigDecimal(10000)).quantity(100).createdAt(LocalDateTime.now()).build();
        productRepository.save(product);
        boolean result = productService.isExist(1L);
        System.out.println(result);
    }

    @Test
    public void 모든상품조회(Pageable pageable) {
        List<ProductDTO> productDTOList = productService.getProductAll(pageable);
        System.out.println(productDTOList);
    }

    @Test
    public void 상품찜하기() throws Exception{
        Long userId = 1L;
        Long productId = 1L;
        boolean result = productService.likedProduct(userId, productId);
        System.out.println(result);
    }

    @Test
    public void 찜한상품조회(Pageable pageable) {
        Long userId = 1L;
        List<ProductDTO> productDTOList = productService.getLikedProduct(userId, pageable);
        System.out.println(productDTOList);
    }

    @Test
    public void 찜하지않은상품조회(Pageable pageable) {
        Long userId = 1L;
        List<ProductDTO> productDTOList = productService.getUnlikedProduct(userId, pageable);
        System.out.println(productDTOList);
    }
}