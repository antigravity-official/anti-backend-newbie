package antigravity.controller;

import antigravity.entity.Heart;
import antigravity.entity.ViewCount;
import antigravity.payload.LikeResponse;
import antigravity.repository.ProductRepository;
import antigravity.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private final Long memberId = 2L;
    private final Long productId = 1L;

    @InjectMocks
    private ProductController controller;

    @Mock
    ProductService service;

    @DisplayName("찜 상품 등록 테스트")
    @Test
    public void likeProductTest() {
        when(service.isAlreadyLiked(memberId, productId)).thenReturn(1);
        ResponseEntity<LikeResponse> response = controller.likeProduct(memberId, productId);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    }
}
