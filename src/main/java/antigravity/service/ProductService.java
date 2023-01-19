package antigravity.service;

import antigravity.entity.Product;
import antigravity.exception.CMResDto;
import antigravity.exception.CustomException;
import antigravity.payload.ProductResponse;
import antigravity.repository.LikedRepository;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final LikedRepository likedRepository;

    public void increaseViewAndTotalLiked(Long productId) {
        Product productEntity = productRepository.findById(productId).orElseThrow(() -> new CustomException("잘못된 상품 id 입니다"));
        productEntity.update(productEntity.getViewed()+1, productEntity.getTotalLiked()+1);
    }

    public void getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException("존재하지 않는 상품 ID 입니다"));
    }

    public List<ProductResponse> getProductResponseList(List<Long> likedProductIdList,
                                                        Pageable pageable, List<ProductResponse> responseList) {

        List<Product> productList = productRepository.findProduct(pageable);
        //제품에 liked = true 붙여서 response 만들기
        for (Product product : productList) {
            ProductResponse response = ProductResponse.likedDefaultFalseBuild(product);
            if (likedProductIdList.contains(product.getId())) {
                response.setLiked(true);
            }
            responseList.add(response);
        }
        return responseList;
    }

    public List<ProductResponse> getLikedProductResponseList(List<Long> likedProductIdList,
                                                             Pageable pageable, List<ProductResponse> responseList) {

        List<Product> productList = productRepository.findByIdIn(likedProductIdList, pageable);
        for (Product product : productList) {
            ProductResponse response = ProductResponse.likedDefaultTrueBuild(product);
            responseList.add(response);
        }
        return responseList;
    }

    public List<ProductResponse> getNoLikedProductResponseList(List<Long> likedProductIdList,
                                                               Pageable pageable, List<ProductResponse> responseList) {

        List<Product> productList = productRepository.findByIdNotIn(likedProductIdList,pageable);
        for (Product product : productList) {
            ProductResponse response = ProductResponse.likedDefaultFalseBuild(product);
            responseList.add(response);
        }
        return responseList;
    }

    public ResponseEntity<CMResDto<?>> getLikedProductList(String liked, Pageable pageable, Long userId) {

        List<Long> likedProductIdList = likedRepository.findLikedProductId(userId);
        List<ProductResponse> responseList= new ArrayList<>();
        String message = "";

        // 상품 조회 - liked = true 설정
        if (liked == null || liked.equals("")) {
            responseList= getProductResponseList(likedProductIdList, pageable, responseList);
            message = "상품 목록 - 찜한 상품 liked = true";
        }
        //라이크 true면 찜한 상품만;
        else if (liked.equals("true")) {
            responseList = getLikedProductResponseList(likedProductIdList, pageable, responseList);
            message = "찜한 상품 목록";
        }
        //라이크 false면 찜하지 않은 상품만;
        else if (liked.equals("false")){
            responseList = getNoLikedProductResponseList(likedProductIdList, pageable, responseList);
            message = "찜하지 않은 상품 목록";
        }
        else {
            throw new CustomException("잘못된 파라미터 입니다.");
        }
        return new ResponseEntity<>(new CMResDto<List<ProductResponse>>(
                "200 OK",message,responseList),HttpStatus.OK);
    }
}
