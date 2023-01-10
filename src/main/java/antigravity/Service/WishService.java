package antigravity.Service;

import antigravity.entity.Product;
import antigravity.entity.Users;
import antigravity.entity.Wish;
import antigravity.exception.ErrorMessage;
import antigravity.payload.ResponseDto;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import antigravity.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class WishService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final WishRepository wishRepository;

    @Transactional
    public ResponseEntity<ResponseDto> createLikedItems(Long productId, Long userId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(ErrorMessage.NOT_EXIST_PRODUCT.getMsg()));
        Users users = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException(ErrorMessage.NOT_EXIST_USER.getMsg()));

        Optional<Wish> wish = wishRepository.findByProductAndUsers(product,users);

        if(wish.isEmpty()){ // 좋아요가 안눌러져있는경우
            wishRepository.save(Wish.builder().product(product).users(users).build());
        }
        else{
            if(wish.get().isWish()){ // 이미 좋아요 눌러져있을경우
                throw new RejectedExecutionException(ErrorMessage.EXIST_LIKED_PRODUCT.getMsg());
            }
            else{ // 좋아요 상태가 false 로 되어있는 경우 ,,
                wish.get().changeWish();
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("찜 등록에 성공하셨습니다", 201));
    }
}
