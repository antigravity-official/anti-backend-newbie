package antigravity.repository;

import antigravity.entity.Bookmark;
import antigravity.entity.ProductHits;
import antigravity.entity.User;
import antigravity.payload.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Transactional
public class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductHitsRepository productHitsRepository;

    @BeforeEach
    @DisplayName("1번 유저와 2번 유저가 각각 20개씩 상품을 찜함")
    public void bookmarkDataSave(){
        for (long i = 1; i < 3; i++) {
            for (long j = 1; j < 21; j++) {
                Long userId = i;
                Long productId = j;

                User user = userRepository.findById(userId).get();

                Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserIdAndProductId(userId,productId);

                if(optionalBookmark.isEmpty()){
                    Bookmark bookmark = Bookmark.bookmarkBuilder(productId, user);

                    bookmarkRepository.save(bookmark);
                    Optional<ProductHits> optionalProductHits = productHitsRepository.findByProductId(productId);
                    if(optionalProductHits.isPresent()){
                        ProductHits productHits = optionalProductHits.get();
                        productHits.increaseHits(productHits.getHits());
                    }else{
                        productHitsRepository.save(ProductHits.productHitsBuilder(productId));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Bookmark 조회 Liked true 성공 테스트")
    public void findByLikedAndUserIdLikedTrueSuccessTest(){
        Long userId = 1L;
        PageRequest pageRequest = PageRequest.of(0,10);

        Page<ProductResponse> productResponsePage = bookmarkRepository.findByLikedAndUserId(true, pageRequest, userId);

        for(ProductResponse p: productResponsePage){
            assertThat(bookmarkRepository.findByUserIdAndProductId(userId,p.getId()).isPresent()).isTrue();
        }

        assertEquals(productResponsePage.getTotalElements(),10);
        assertEquals(productResponsePage.getTotalPages(),1);
    }

    @Test
    @DisplayName("Bookmark 조회 Liked true 실패 테스트")
    public void findByLikedAndUserIdLikedTrueFailTest(){
        Long userId = 3L;
        PageRequest pageRequest = PageRequest.of(0,10);

        Page<ProductResponse> productResponsePage = bookmarkRepository.findByLikedAndUserId(true, pageRequest, userId);

        for(ProductResponse p: productResponsePage){
            assertThat(bookmarkRepository.findByUserIdAndProductId(2L,p.getId()).isPresent()).isFalse();
        }
    }

    @Test
    @DisplayName("Bookmark 조회 Liked false 성공 테스트")
    public void findByLikedAndUserIdLikedFalseSuccessTest(){
        Long userId = 4L;
        PageRequest pageRequest = PageRequest.of(0,10);

        Page<ProductResponse> productResponsePage = bookmarkRepository.findByLikedAndUserId(false, pageRequest, userId);

        // 찜 목록에 21~26번까지는 없으니 21~26번까지 조회되야함
        assertEquals(productResponsePage.getTotalElements(),6);
    }

    @Test
    @DisplayName("Bookmark 조회 Liked null 성공 테스트")
    public void findByLikedAndUserIdLikedNullSuccessTest(){
        Long userId = 1L;
        PageRequest pageRequest = PageRequest.of(0,10);

        Page<ProductResponse> productResponsePage = bookmarkRepository.findByLikedAndUserId(null, pageRequest, userId);

        for(ProductResponse p: productResponsePage){
            assertEquals(p.getLiked(),true);
        }
    }
}
