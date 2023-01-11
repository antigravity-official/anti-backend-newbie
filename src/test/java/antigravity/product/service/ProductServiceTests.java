package antigravity.product.service;

import antigravity.global.exception.AntiException;
import antigravity.global.redis.RedisDao;
import antigravity.product.domain.entity.DipProduct;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.DipProductRepository;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.service.impl.ProductServiceImpl;
import antigravity.product.service.impl.ViewServiceImpl;
import antigravity.product.web.dto.DipProductResponse;
import antigravity.user.entity.User;
import antigravity.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DipProductRepository dipProductRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ViewServiceImpl viewService;
    @InjectMocks
    private ProductServiceImpl productService;
    User user;
    Product product;
    DipProduct dipProduct;
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
        dipProduct = DipProduct.builder()
                .id(1L)
                .userId(user.getId().intValue())
                .productId(product.getId())
                .build();
    }

    @Test
    @DisplayName("유저가 존재하지 않을 경우 400 에러를 발생시킨다.")
    void notExistUser() {
        AntiException exception = assertThrows(AntiException.class, () -> {
            productService.createDip(3,3L);
        });
        assertEquals("해당 아이디를 가진 유저가 존재하지 않습니다.",exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    @DisplayName("상품이 존재하지 않을 경우 400 에러를 발생시킨다.")
    void notExistProduct() {
        //mocking
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        //when
        AntiException exception = assertThrows(AntiException.class, () -> {
            productService.createDip(user.getId().intValue(),3L);
        });

        //then
        assertEquals("해당 아이디를 가진 상품이 존재하지 않습니다.",exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    @DisplayName("이미 찜했을 경우 400 에러를 발생시킨다.")
    void alreadyDipProduct() {
        //mocking
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(dipProductRepository.existsDipProductByUserIdAndProductId(any(),any())).willReturn(1);

        //when
        AntiException exception = assertThrows(AntiException.class, () -> {
            productService.createDip(user.getId().intValue(),product.getId());
        });

        //then
        assertEquals("이미 찜한 상품입니다.",exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    @DisplayName("상품을 찜할 수 있다.")
    void dipProduct() {
        //mocking
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(dipProductRepository.existsDipProductByUserIdAndProductId(any(),any())).willReturn(0);
        given(viewService.addViewCntToRedis(any())).willReturn(1L);
        given(dipProductRepository.save(any())).willReturn(dipProduct.getProductId());

        //when
        DipProductResponse dipProductResponse = productService.createDip(user.getId().intValue(),product.getId());

        //then
        assertEquals(dipProductResponse.getProductId(), product.getId());
        assertEquals(Optional.ofNullable(dipProductResponse.getUserId()), Optional.ofNullable(user.getId().intValue()));
        assertEquals(Optional.ofNullable(dipProductResponse.getViewed()), Optional.ofNullable(1L));
    }

}
