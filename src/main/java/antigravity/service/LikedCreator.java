package antigravity.service;


import antigravity.entity.LikedValidator;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.entity.View;
import antigravity.payload.BadRequestException;
import antigravity.payload.LikedDto.Create;
import antigravity.payload.ProductConstants;
import antigravity.repository.MemberRepository;
import antigravity.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 찜 상품 등록 service
 */
@Service
@RequiredArgsConstructor
public class LikedCreator {

    private final MemberRepository memberRepository;

    private final ViewRepository viewRepository;

    private final LikedRetriever retriever;

    /**
     * 찜 상품 등록
     *
     * @param condition db 조회 객체
     * @return 찜 등록한 응답 객체
     */
    @Transactional
    public Create.Response create(Create.Condition condition) {
        Member member = findMember(condition.getMemberId());
        Product product = retriever.findProduct(condition.getProductId(), LikedValidator::deletedProduct);

        // 이미 등록된 찜인지 체크
        LikedValidator.alreadyLiked(retriever.findByLiked(condition.getProductId(), condition.getMemberId()));

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
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ProductConstants.NOT_EXISTS_USER));
        LikedValidator.deletedMember(member);
        return member;
    }


}
