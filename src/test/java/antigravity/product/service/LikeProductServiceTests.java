package antigravity.product.service;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.dao.LikeProductDAO;
import antigravity.product.domain.dao.ProductDAO;
import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.entity.Product;
import antigravity.product.service.impl.LikeProductServiceImpl;
import antigravity.product.service.impl.ViewServiceImpl;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.user.entity.User;
import antigravity.user.repository.UserRepository;
import antigravity.user.service.UserService;
import antigravity.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(SpringExtension.class)
public class LikeProductServiceTests {
    @Mock
    private LikeProductDAO likeProductDAO;
    @Mock
    private ViewServiceImpl viewService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private LikeProductServiceImpl likeProductService;

    User user;
    Product product;
    LikeProduct likeProduct;
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
    }

    @Test
    @DisplayName("상품을 찜할 수 있다.")
    void dipProduct() {
        //mocking
        willDoNothing().given(productDAO).validateExistProduct(any());
        willDoNothing().given(likeProductDAO).checkAlreadyDip(any(),any());
        given(viewService.addViewCntToRedis(any())).willReturn(1L);
        given(likeProductDAO.saveLikeProduct(any())).willReturn(likeProduct.getProductId());

        //when
        LikeProductResponse likeProductResponse = likeProductService.createLikeProduct(user.getId().intValue(),product.getId());

        //then
        assertEquals(likeProductResponse.getProductId(), product.getId());
        assertEquals(Optional.ofNullable(likeProductResponse.getUserId()), Optional.ofNullable(user.getId().intValue()));
        assertEquals(Optional.ofNullable(likeProductResponse.getViewed()), Optional.ofNullable(1L));
    }

    @Test
    @DisplayName("찜상품을 찾을 수 있다.")
    void findLikeProduct() {
        //mocking
        given(productDAO.findById(any())).willReturn(product);
        given(likeProductDAO.calculateTotalDip(any())).willReturn(3);
        given(likeProductDAO.findAllByUserId(any(),any())).willReturn(new PageImpl<>(likeProducts));

        //when
        Page<ProductResponse> likeProducts = likeProductService.findLikeProduct(user.getId().intValue(), PageRequest.of(0, 10));

        //then
        Iterator<ProductResponse> it = likeProducts.iterator();
        while(it.hasNext()) {
            ProductResponse next = it.next();
            assertEquals(next.getLiked(), true);
        }
    }
}
