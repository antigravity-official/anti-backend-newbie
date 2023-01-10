package antigravity.service;

import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public String createFavorite(Long userId, Long productId){
        String result;

        // 사용자 정보 조회
        boolean userCheck = userRepository.findId(userId);
        Product product = productRepository.findById(productId);

        if(!userCheck){
            // 사용자 정보가 없는 경우
            result = "400";
        } else if (Objects.isNull(product)) {
            //상품 정보가 없는 경우
            result = "400";
        }else{
            // 찜 등록
            result = productRepository.productLiked(userId,productId);
        }

        return result;
    }
}
