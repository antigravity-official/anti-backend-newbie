package antigravity.service;

import antigravity.config.PageParam;
import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * 찜 상품 등록
     * @date 2023.01.09
     * @author 이상우
     * @param productId
     * @param userId
     * @return String
     * */
    public String createFavorite(Long userId, Long productId){
        String result;

        // 사용자 정보 조회
        boolean userCheck = userRepository.findId(userId);
        Product product = productRepository.findById(productId);

        if(!userCheck){
            // 사용자 정보가 없는 경우
            result = "401";
        } else if (Objects.isNull(product)) {
            //상품 정보가 없는 경우
            result = "402";
        }else{
            // 찜 등록
            result = productRepository.productLiked(userId,productId);
        }

        return result;
    }

    /**
     * 상품 조회
     * @date 2023.01.12
     * @author 이상우
     * @param param
     * @param id
     * @param liked
     *        liked = false 찜하지 않은 제품 목록
     *        liked = true 찜한 제품 목록
     * @return List<ProductResponse>
     * */
    public List<ProductResponse> favoriteProductList(PageParam param, Long id, boolean liked){
        List<ProductResponse> productResponseList;

        boolean userCheck = userRepository.findId(id);

        if(!userCheck){
            // 사용자 정보가 없는 경우
            productResponseList = null;
        } else{
            productResponseList = productRepository.favoriteProductList(param,id, liked);
        }

        return productResponseList;
    }

    /**
     * 전체 상품 조회 및 찜 상품 true
     * @date 2023.01.12
     * @author 이상우
     * @param param
     * @param id
     * @return List<ProductResponse>
     * */
    public List<ProductResponse> findAllProductList(PageParam param, Long id){
        List<ProductResponse> productResponseList;

        boolean userCheck = userRepository.findId(id);

        if(!userCheck){
            // 사용자 정보가 없는 경우
            productResponseList = null;
        } else{
            productResponseList = productRepository.findAllProductList(param,id);
        }

        return productResponseList;
    }
}
