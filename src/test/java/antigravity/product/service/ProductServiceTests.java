package antigravity.product.service;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.dao.LikeProductDAO;
import antigravity.product.domain.dao.ProductDAO;
import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.LikeProductRepository;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.service.impl.ProductServiceImpl;
import antigravity.product.service.impl.ViewServiceImpl;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.user.entity.User;
import antigravity.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private LikeProductDAO likeProductDAO;
    @Mock
    private ProductDAO productDAO;
    @InjectMocks
    private ProductServiceImpl productService;
    User user;
    Product product;
    LikeProduct likeProduct;
    List<Product> products = new LinkedList<>();
    List<LikeProduct> likeProducts = new LinkedList<>();

    @BeforeEach
    void setting() {
        user = User.builder()
                .name("조소연")
                .id(1L)
                .createdAt(LocalDateTime.now())
                .build();
        product = Product.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .viewed(1L)
                .name("No1. 더핏세트")
                .quantity(10)
                .price(BigDecimal.valueOf(42800))
                .sku("G2000000019")
                .build();
        likeProduct = LikeProduct.builder()
                .id(1L)
                .userId(user.getId().intValue())
                .productId(product.getId())
                .build();
        for (int i = 0; i < 100; i++) {
            likeProducts.add(LikeProduct.builder()
                    .id(i+2L)
                    .userId(user.getId().intValue())
                    .productId(product.getId())
                    .build());
        }
        for (int i = 0; i < 100; i++) {
            product = Product.builder()
                    .createdAt(LocalDateTime.now())
                    .viewed(1L)
                    .name("No1. 더핏세트")
                    .quantity(10)
                    .price(BigDecimal.valueOf(42800))
                    .sku("G2000000019")
                    .build();;
        }
    }
    @Test
    @DisplayName("찜하지 않은 상품을 찾을 수 있다.")
    void findNotLikeProduct() {
        //mocking
        given(likeProductDAO.findAllByUserId(any(),any())).willReturn(new PageImpl<>(likeProducts));
        given(productDAO.findAllNotDipProduct(any(),any())).willReturn(new PageImpl<>(products));
        //when
        Page<ProductResponse> likeProducts = productService.findNotLikeProductList(user.getId().intValue(), PageRequest.of(0, 10));

        //then
        Iterator<ProductResponse> it = likeProducts.iterator();
        while(it.hasNext()) {
            ProductResponse next = it.next();
            assertEquals(next.getLiked(), false);
        }
    }


}
