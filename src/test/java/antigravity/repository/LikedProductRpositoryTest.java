package antigravity.repository;

import antigravity.domain.LikedProductTestBuilder;
import antigravity.entity.LikedProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LikedProductRpositoryTest {
    @Autowired
    private LikedProductRpository likedProductRpository;

    @Nested
    class CountByProductId {
        @Test
        void sucess() {
            LikedProduct likedProduct0 = LikedProductTestBuilder.createLikedProduct0();
            likedProductRpository.save(likedProduct0);

            LikedProduct likedProduct1 = LikedProductTestBuilder.createLikedProduct1();
            likedProductRpository.save(likedProduct1);

            Long likedProductCount = likedProductRpository.countByProductId(1L);
            Assertions.assertThat(likedProductCount).isEqualTo(2L);
        }

    }

}
