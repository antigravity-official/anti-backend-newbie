package antigravity.utils;

import antigravity.domain.ProductTestBuilder;
import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelMapperTest {
    @Autowired
    private ModelMapper modelMapper;

    @Nested
    class ConvertTest {
        @Test
        void sucess() {
            Product likedProduct0 = ProductTestBuilder.createLikedProduct0();
            ProductResponse productResponse = modelMapper.map(likedProduct0, ProductResponse.class);
            Assertions.assertThat(productResponse.getId()).isEqualTo(1L);
            Assertions.assertThat(productResponse.getSku()).isEqualTo("G2000000019");
            Assertions.assertThat(productResponse.getName()).isEqualTo("No1. 더핏세트");
            Assertions.assertThat(productResponse.getPrice()).isEqualTo(BigDecimal.valueOf(42800));
        }
    }
}

