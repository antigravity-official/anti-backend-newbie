package antigravity.product.web;

import antigravity.product.domain.entity.Product;
import antigravity.product.service.LikeProductService;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.product.web.presenter.ProductPresenter;
import antigravity.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProductPresenter productPresenter;
    @MockBean
    private UserService userService;
    @MockBean
    private LikeProductService likeProductService;
    LikeProductResponse likeProductResponse;
    List<ProductResponse> notDipProductResponses = new LinkedList<>();
    List<ProductResponse> dipProductResponses = new LinkedList<>();
    List<ProductResponse> allResponses = new LinkedList<>();

    @BeforeEach
    void settings() {
        likeProductResponse = LikeProductResponse.builder()
                .productId(1L)
                .userId(1)
                .viewed(1L)
                .build();
        for (int i = 0; i < 100; i++) {
            notDipProductResponses.add(ProductResponse.createNotDipProduct(Product.builder()
                    .id(i+1L)
                    .createdAt(LocalDateTime.now())
                    .viewed(1L)
                    .name("No1. 더핏세트")
                    .quantity(10)
                    .price(BigDecimal.valueOf(42800))
                    .sku("G2000000019")
                    .build(),1));
            dipProductResponses.add(ProductResponse.createDipProduct(Product.builder()
                    .id(i+1L)
                    .createdAt(LocalDateTime.now())
                    .viewed(1L)
                    .name("No1. 더핏세트")
                    .quantity(10)
                    .price(BigDecimal.valueOf(42800))
                    .sku("G2000000019")
                    .build(),1));
        }
        allResponses.addAll(notDipProductResponses);
        allResponses.addAll(dipProductResponses);
    }

    @Test
    @DisplayName("유저 아이디를 헤더로 넣지 않았을 때 400에러가 발생한다.")
    void dipProductNotExistUserID() throws Exception {
        mockMvc.perform(post("/products/liked/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("찜 상품을 등록할 수 있다.")
    void dipProduct() throws Exception {
        //when
        when(likeProductService.createLikeProduct(any(), any())).thenReturn(likeProductResponse);

        //then
        mockMvc.perform(post("/products/liked/1")
                        .header("X-USER-Id",1))
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("모든 상품을 조회할 수 있다.")
    void findAllProduct() throws Exception {
        //when
        when(productPresenter.showProducts(any(), eq(null), any()))
                .thenReturn(new PageImpl<>(allResponses));

        //then
        mockMvc.perform(get("/products/liked/")
                        .header("X-USER-Id",1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모든 상품을 10개씩 조회할 수 있다.")
    void findAllProductOnly10() throws Exception {
        //when
        when(productPresenter.showProducts(any(), eq(null), any()))
                .thenReturn(new PageImpl<>(allResponses.subList(0,10)));

        //then
        mockMvc.perform(get("/products/liked/")
                        .header("X-USER-Id",1)
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("찜한 상품을 조회할 수 있다.")
    void findAllDipProduct() throws Exception {
        //when
        when(productPresenter.showProducts(any(), eq(true), any()))
                .thenReturn(new PageImpl<>(dipProductResponses.subList(5,10)));

        //then
        mockMvc.perform(get("/products/liked/")
                        .header("X-USER-Id",1)
                        .param("liked","true")
                        .param("size","5")
                        .param("page","5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("찜하지 않은 상품을 조회할 수 있다.")
    void findAllNotDipProduct() throws Exception {
        //when
        when(productPresenter.showProducts(any(), eq(false), any()))
                .thenReturn(new PageImpl<>(notDipProductResponses));

        //then
        mockMvc.perform(get("/products/liked/")
                        .header("X-USER-Id",1)
                        .param("liked","false"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
