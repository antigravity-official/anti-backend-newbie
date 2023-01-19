package antigravity.service;

import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("서비스 - 제품 요청 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProductResponseServiceTest {

    @InjectMocks
    private ProductResponseService sut;
    @Mock
    private ProductRepository productRepository;

    @DisplayName("검색 조건 없이 제품 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingProduct_thenReturnsEntireEventList() {
    }
}