package antigravity.service;

import antigravity.entity.*;
import antigravity.exception.AlreadyBookmarkException;
import antigravity.exception.NotFoundProductException;
import antigravity.repository.BookmarkRepository;
import antigravity.repository.ProductHitsRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BookmarkServiceTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductHitsRepository productHitsRepository;

    @Test
    @DisplayName("찜 등록 다수 유저 성공 테스트")
    public void createUserBookmarkSuccessManyUserTest(){
        ProductHits productHits = null;

        for (long i = 1; i < 5; i++) {
            Long userId = i;
            Long productId = 1L;

            productValidation(productId);

            User user = userValidation(userId);

            Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserIdAndProductId(userId,productId);

            if(optionalBookmark.isEmpty()){
                Bookmark bookmark = Bookmark.bookmarkBuilder(productId, user);

                bookmarkRepository.save(bookmark);
                Optional<ProductHits> optionalProductHits = productHitsRepository.findByProductId(productId);
                if(optionalProductHits.isPresent()){
                    productHits = optionalProductHits.get();
                    productHits.increaseHits(productHits.getHits());
                }else{
                    productHits = productHitsRepository.save(ProductHits.productHitsBuilder(productId));
                }
            }else{
                throw new AlreadyBookmarkException("이미 찜에 등록된 상품입니다.");
            }
        }

        assertEquals(productHits.getHits(),4L);
    }

    @Test
    @DisplayName("찜 등록 다수 상품 성공 테스트")
    public void createUserBookmarkSuccessManyProductTest(){
        ProductHits productHits = null;

        for (long i = 1; i < 21; i++) {
            Long userId = 1L;
            Long productId = i;

            productValidation(productId);

            User user = userValidation(userId);

            Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserIdAndProductId(userId,productId);

            if(optionalBookmark.isEmpty()){
                Bookmark bookmark = Bookmark.bookmarkBuilder(productId, user);

                bookmarkRepository.save(bookmark);
                Optional<ProductHits> optionalProductHits = productHitsRepository.findByProductId(productId);
                if(optionalProductHits.isPresent()){
                    productHits = optionalProductHits.get();
                    productHits.increaseHits(productHits.getHits());
                }else{
                    productHits = productHitsRepository.save(ProductHits.productHitsBuilder(productId));
                }
            }else{
                throw new AlreadyBookmarkException("이미 찜에 등록된 상품입니다.");
            }
        }

        List<Bookmark> all = bookmarkRepository.findAll();
        assertEquals(all.size(),20);
        assertEquals(productHits.getHits(),1L);
    }

    @Test
    @DisplayName("찜 등록 실패 테스트")
    public void createUserBookmarkFailTest(){
        Long userId = 1L;
        Long productId = 1L;

        productValidation(productId);

        User user = userValidation(userId);

        Throwable exception = null;

        // 찜 등록
        bookmarkRepository.save(Bookmark.bookmarkBuilder(productId, user));

        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserIdAndProductId(userId,productId);

        if(optionalBookmark.isEmpty()){
            Bookmark bookmark = Bookmark.bookmarkBuilder(productId, user);

            bookmarkRepository.save(bookmark);
            Optional<ProductHits> optionalProductHits = productHitsRepository.findByProductId(productId);

            if(optionalProductHits.isPresent()){
                ProductHits productHits = optionalProductHits.get();
                assertEquals(productHits.increaseHits(productHits.getHits()),1L);
            }

            ProductHits producthits = productHitsRepository.save(ProductHits.productHitsBuilder(productId));
            assertEquals(producthits.getHits(),1);
        }else{
            exception = assertThrows(RuntimeException.class, () -> {
                throw new AlreadyBookmarkException("이미 찜에 등록된 상품입니다.");
            });
        }

        assertEquals("이미 찜에 등록된 상품입니다.", Objects.requireNonNull(exception).getMessage());

    }


    @Test
    @DisplayName("등록되지 않은 유저 조회 실패 테스트")
    public void createUserBookmarkNotRegisterUserFailTest(){
        // given
        Long userId  = 32L;
        // when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            userValidation(userId);
        });
        // then
        assertEquals("유저 정보를 찾을 수 없습니다.",exception.getMessage());
    }

    @Test
    @DisplayName("등록되지 않은 상품 조회 실패 테스트")
    public void createUserBookmarkNotRegisterProductFailTest(){
        // given
        Long productId  = 32L;
        // when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            productValidation(productId);
        });
        // then
        assertEquals("등록되지 않은 상품입니다.",exception.getMessage());
    }

    private Product productValidation(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundProductException("등록되지 않은 상품입니다."));
    }

    private User userValidation(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저 정보를 찾을 수 없습니다."));
    }
}