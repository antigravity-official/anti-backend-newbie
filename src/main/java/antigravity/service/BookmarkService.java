package antigravity.service;

import antigravity.entity.Bookmark;
import antigravity.entity.Product;
import antigravity.entity.ProductHits;
import antigravity.entity.User;
import antigravity.exception.AlreadyBookmarkException;
import antigravity.exception.NotFoundProductException;
import antigravity.repository.BookmarkRepository;
import antigravity.repository.ProductHitsRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductHitsRepository productHitsRepository;

    @Transactional
    public long createUserBookmark(Long userId, Long productId){
        productValidation(productId);

        User user = userValidation(userId);

        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserIdAndProductId(userId,productId);

        if(optionalBookmark.isEmpty()){
            Bookmark bookmark = Bookmark.bookmarkBuilder(productId, user);

            bookmarkRepository.save(bookmark);

            Optional<ProductHits> optionalProductHits = productHitsRepository.findByProductId(productId);

            if(optionalProductHits.isPresent()){
                ProductHits productHits = optionalProductHits.get();
                return productHits.increaseHits(productHits.getHits());
            }

            ProductHits productHits = productHitsRepository.save(ProductHits.productHitsBuilder(productId));

            return productHits.getHits();
        }

        throw new AlreadyBookmarkException("이미 찜에 등록된 상품입니다.");
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
