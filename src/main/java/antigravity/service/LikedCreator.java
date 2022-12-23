package antigravity.service;


import antigravity.entity.LikedStatus;
import antigravity.entity.LikedValidator;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.entity.View;
import antigravity.entity.dto.LikedDto.Create;
import antigravity.entity.dto.exception.BadRequestException;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;


/**
 * 찜 상품 등록 service
 */
@Service
@RequiredArgsConstructor
public class LikedCreator {

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

    private final ViewRepository viewRepository;

    /**
     * 찜 상품 등록
     *
     * @param condition db 조회 객체
     * @return 찜 등록한 응답 객체
     */
    @Transactional
    public Create.Response create(Create.Condition condition) {
        Member member = findMember(condition.getMemberId());
        Product product = findProduct(condition.getProductId(), LikedValidator::deletedProduct);

        // 이미 등록된 찜인지 체크
        LikedValidator.alreadyLiked(viewRepository.findAllByProductIdAndMemberIdAndLikedStatus(condition.getProductId(), condition.getMemberId(),
                                                                                               LikedStatus.LIKED));

        // 찜 등록
        View result = viewRepository.save(new View(product, member, condition.getLikedStatus()));

        return new Create.Response(result);
    }

    /**
     * member finder 에 있어야할 기능
     * member 조회
     *
     * @param memberId 조회할 사용자
     */
    private Member findMember(Long memberId) {
        // goto member finder
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException("사용자가 없습니다."));
        LikedValidator.deletedMember(member);
        return member;
    }

    /**
     * 상품 조회
     *
     * @param productId     상품 id
     * @param afterConsumer validation 작업
     */
    @Transactional(readOnly = true)
    public Product findProduct(Long productId, Consumer<Product> afterConsumer) {
        Product result = productRepository.findById(productId).orElseThrow(() -> new BadRequestException("상품이 없습니다."));

        if (null != afterConsumer) {
            afterConsumer.accept(result);
        }
        return result;
    }


}
