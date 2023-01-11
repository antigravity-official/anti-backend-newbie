package antigravity.product.repository;

import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.LikeProductRepository;
import antigravity.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LikeProductRepository likeProductRepository;
    Product product;
    @BeforeEach
    void setting() {
        product = Product.builder()
                .createdAt(LocalDateTime.now())
                .viewed(1L)
                .name("No1. 더핏세트")
                .quantity(10)
                .price(BigDecimal.valueOf(42800))
                .sku("G2000000019")
                .build();
    }
    @Test
    @DisplayName("상품을 저장한 뒤 상품 아이디로 찾을 수 있다.")
    void saveAndFind() throws Exception {
        //when
        Long id = productRepository.save(product);
        Product findProduct = productRepository.findById(id).get();

        //then
        assertEquals(id, findProduct.getId());
    }
    @Test
    @DisplayName("상품 아이디로 조회수를 찾을 수 있다.")
    void findViewCnt() throws Exception {
        //when
        Long id = productRepository.save(product);
        Long viewCnt = productRepository.findProductViewCnt(id);

        //then
        assertEquals(product.getViewed(), viewCnt);
    }

    @Test
    @DisplayName("상품 아이디로 조회수를 수정할 수 있다.")
    void updateViewCnt() throws Exception {
        //when
        Long id = productRepository.save(product);
        productRepository.updateViewCntFromRedis(id, 100L);

        Product findProduct = productRepository.findById(id).get();

        //then
        assertEquals(findProduct.getViewed(), 100L);
    }

    @Test
    @DisplayName("모든 상품을 찾을 수 있다.")
    void findAllProductById() {
        //when
        for (int i = 0; i < 100; i++) {
            productRepository.save(product);
        }
        Page<Product> products = productRepository.findAll(PageRequest.of(0, 10));
        //then
        assertEquals(10, products.getSize());
        assertEquals(0,products.getNumber());
    }

    @Test
    @DisplayName("찜하지 않은 상품을 찾을 수 있다.")
    void findAllNotDipProductById() {
        List<Long> dipProductList = new ArrayList<>();

        //when
        for (int i = 0; i < 100; i++) {
            productRepository.save(product);
        }

        for (int i = 0; i < 20; i++) {
            LikeProduct likeProduct = LikeProduct.builder()
                    .userId(1)
                    .productId(Long.valueOf(i+1))
                    .build();
            dipProductList.add(likeProductRepository.save(likeProduct));
        }

        Iterator it = dipProductList.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() +" ");
        }

        Page<Product> products = productRepository.findAllNotDipProduct(dipProductList, PageRequest.of(0, 10));

        it = products.iterator();
        System.out.println();
        while(it.hasNext()) {
            Product p = (Product) it.next();
            System.out.print(p.getId()+" ");
        }
        //then
        assertEquals(10, products.getSize());
        assertEquals(0,products.getNumber());
    }

    @Test
    @DisplayName("상품 개수를 찾을 수 있다.")
    void countOfDipProductByProductId() {
        //when
        for (int i = 0; i < 10; i++) {
            productRepository.save(product);
        }
        int cnt = productRepository.countProduct();

        //then
        assertEquals(33, cnt);
    }

}
