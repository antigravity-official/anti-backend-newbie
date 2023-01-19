package antigravity.service;


import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("서비스 - 제품 정보 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProductInfoServiceTest {

    @InjectMocks
    private ProductInfoService sut;
    @Mock
    private ProductRepository eventRepository;
}