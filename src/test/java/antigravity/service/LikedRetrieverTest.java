package antigravity.service;

import antigravity.entity.LikedStatus;
import antigravity.payload.LikedDto;
import antigravity.payload.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

class LikedRetrieverTest {

    @DisplayName("상품 조회 boot test - db 데이터 활용")
    @SpringBootTest
    @Nested
    class Boot {

        @Autowired
        LikedCreator creator;
        @Autowired
        LikedRetriever retriever;

        PageRequest defaultPageable = PageRequest.of(0, 10, Sort.by("id").descending());

        @Test
        @DisplayName("성공 - 전체 상품 조회")
        void testSuccessRetrieveAllProducts() {
            Page<ProductResponse> products = retriever.products(new LikedDto.Retrieve.Condition(2L, null, defaultPageable));

            assertAll(() -> assertNotNull(products),
                      () -> assertEquals(10, products.getPageable().getPageSize()),
                      () -> assertEquals(0, products.getPageable().getOffset()),
                      () -> assertEquals(23L, products.getContent().get(0).getId()),
                      () -> assertEquals(22L, products.getContent().get(1).getId()),
                      () -> assertEquals(21L, products.getContent().get(2).getId()),
                      () -> assertEquals(20L, products.getContent().get(3).getId())
                     );
        }

        @Test
        @DisplayName("성공 - 찜 상품 조회")
        void testSuccessRetrieveAllProductsByLikedOne() {
            creator.create(new LikedDto.Create.Condition(12L, 2L, LikedStatus.LIKED));
            Page<ProductResponse> products = retriever.products(new LikedDto.Retrieve.Condition(2L, LikedStatus.LIKED.liked(), defaultPageable));

            assertEquals(12L, products.getContent().get(0).getId());
            assertEquals(1, products.getContent().size());
        }


        @Test
        @DisplayName("성공 - 찜 상품 제외 조회")
        void testSuccessRetrieveAllProductsByUnLiked() {
            creator.create(new LikedDto.Create.Condition(20L, 2L, LikedStatus.LIKED));
            Page<ProductResponse> products = retriever.products(new LikedDto.Retrieve.Condition(2L, LikedStatus.UNLIKED.liked(), defaultPageable));

            // pageable
            assertEquals(10, products.getContent().size());
            assertEquals(21, products.getTotalElements());

            // content
            assertAll(
                    () -> assertEquals(23L, products.getContent().get(0).getId()),
                    () -> assertEquals(22L, products.getContent().get(1).getId()),
                    () -> assertEquals(21L, products.getContent().get(2).getId()),
                    () -> assertEquals(19L, products.getContent().get(3).getId()),
                    () -> assertEquals(18L, products.getContent().get(4).getId()),
                    () -> assertEquals(17L, products.getContent().get(5).getId()));

            products.forEach(s -> assertNotEquals(20L, s.getId()));
        }
    }

}
