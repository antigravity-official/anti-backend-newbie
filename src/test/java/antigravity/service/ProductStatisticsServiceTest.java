package antigravity.service;

import antigravity.domain.ProductTestBuilder;
import antigravity.entity.Product;
import antigravity.entity.ProductStatistics;
import antigravity.repository.ProductRepository;
import antigravity.repository.ProductStatisticsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductStatisticsServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductStatisticsRepository productStatisticsRepository;

    @Autowired
    @InjectMocks
    private ProductStatisticsService productStatisticsService;

    @Nested
    class IncreaseViewCount {
        @Test
        void success() {
            // given
            Product likedProduct0 = ProductTestBuilder.createLikedProduct0();
            when(productRepository.findById(1L)).thenReturn(likedProduct0);
            doNothing().when(productStatisticsRepository).save(any());

            // when
            productStatisticsService.increaseViewCount(1L);
            Integer viewCount = likedProduct0.getProductStatistics().getViewCount();

            // then
            assertThat(viewCount).isEqualTo(1);
        }
    }
}