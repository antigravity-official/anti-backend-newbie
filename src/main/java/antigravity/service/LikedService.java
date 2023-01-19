package antigravity.service;

import antigravity.entity.Liked;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.repository.LikedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikedService {

    private final LikedRepository likedRepository;

    public void postLiked(Long userId, Long productId ) {

        int countLiked = likedRepository.countAlreadyLiked(userId, productId);
        if (countLiked == 0) {
            User user = User.builder().id(userId).build();
            Product product = Product.builder().id(productId).build();
            Liked liked = Liked.builder().user(user).product(product).build();
            Liked likedEntity = likedRepository.save(liked);
        }else{
            throw new CustomException("이미 찜을 완료했습니다.");
        }
    }

}
