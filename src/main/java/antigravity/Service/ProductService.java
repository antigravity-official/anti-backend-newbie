package antigravity.Service;

import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductResponse> selectProduct(Boolean liked, Long userId, Pageable pageable) {
        if (liked == null) { //TODO user가 찜한 상품 true
            Page<ProductResponse> allProduct = productRepository.findAllProduct(userId, pageable);
            return allProduct;
        } else { //TODO liked true false 상품 선택
            if(liked == true){
                return productRepository.findProductTrueLike(userId, pageable);
            }
            else{
                return productRepository.findProductFalseLike(userId, pageable);
            }
        }
    }
}
