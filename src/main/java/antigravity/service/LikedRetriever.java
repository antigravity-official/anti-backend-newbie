package antigravity.service;

import antigravity.entity.LikedStatus;
import antigravity.entity.Product;
import antigravity.entity.View;
import antigravity.payload.BadRequestException;
import antigravity.payload.LikedDto;
import antigravity.payload.ProductConstants;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import antigravity.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static antigravity.payload.ProductConstants.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikedRetriever {
    private final ViewRepository viewRepository;
    private final ProductRepository productRepository;

    /**
     * 찜 상품 목록 조회
     * @param condition 조회 조건
     * @return paging 객체
     */
    public Page<ProductResponse> products(LikedDto.Retrieve.Condition condition) {
        switch (condition.getLikedStatus()) {
            case NONE:
                // 전체 상품
                return getProductResponsePage(condition, productRepository.findAll(condition.getPageable()));
            case LIKED:
                // 찜을 누른 상품
                return getProductResponsePage(condition, productRepository.findAllJoinViewByMemberAndLikedStatus(condition.getMemberId(),
                                                                                                                 condition.getLikedStatus(),
                                                                                                                 condition.getPageable()));
            case UNLIKED:
                // 찜을 누르지 않은 상품
                return getProductResponsePage(condition, productRepository.findAllByNotContainsLikedViewMemberId(condition.getMemberId(), condition.getPageable()));


            default:
                throw new BadRequestException();
        }

    }

    private PageImpl<ProductResponse> getProductResponsePage(LikedDto.Retrieve.Condition condition, Page<Product> findAllResult) {
        List<ProductResponse> productResponseList = findAllResult.getContent()
                                                                 .stream()
                                                                 .map(product -> new ProductResponse(product, condition.getMemberId()))
                                                                 .collect(Collectors.toList());
        return new PageImpl<>(productResponseList, findAllResult.getPageable(), findAllResult.getTotalElements());
    }


    /**
     * 상품 조회
     *
     * @param productId     상품 id
     * @param afterConsumer validation 작업
     */
    public Product findProduct(Long productId, Consumer<Product> afterConsumer) {
        Product result = productRepository.findById(productId).orElseThrow(() -> new BadRequestException(NOT_EXISTS_PRODUCT));

        if (null != afterConsumer) {
            afterConsumer.accept(result);
        }
        return result;
    }

    /**
     * 상품 찜 등록 여부 조회
     * @param productId 상품
     * @param memberId  사용자
     * @return LIKED 등록된 객체 반환
     */
    public List<View> findByLiked(Long productId, Long memberId) {
        return viewRepository.findAllByProductIdAndMemberIdAndLikedStatus(productId, memberId, LikedStatus.LIKED);
    }

    /**
     * (임시) 전체 페이지 조회
     * @param memberId 사용자
     * @param pageable 페이징
     */
    public Page<ProductResponse> retrieveAll(Long memberId, Pageable pageable) {
        Page<Product> all = productRepository.findAll(pageable);
        List<ProductResponse> collect = all.getContent()
                                           .stream()
                                           .map(s -> new ProductResponse(s, memberId))
                                           .collect(Collectors.toList());
        return new PageImpl<>(collect, all.getPageable(), all.getTotalElements());
    }
}
