package antigravity.service;

import antigravity.dto.LikeHistorySearchCondition;
import antigravity.entity.LikeHistory;
import antigravity.exception.ExistLikeHistoryException;
import antigravity.payload.ProductResponse;
import antigravity.repository.LikeHistoryRepository;
import antigravity.router.ProductResponseRouter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeHistoryService {
    private final LikeHistoryQueryService likeHistoryQueryService;

    private final MemberQueryService memberQueryService;
    private final ProductQueryService productQueryService;
    private final LikeHistoryRepository likeHistoryRepository;

    private final ProductViewService viewService;

    private final ProductResponseRouter productResponseRouter;

    @Transactional
    public void addNotDuplicatedLikeHistory(Long productId, Long memberId) {
        if (likeHistoryQueryService.exists(memberId, productId)) {
            throw new ExistLikeHistoryException();
        }
        likeHistoryRepository.save(LikeHistory.builder()
                .member(memberQueryService.findById(memberId))
                .product(productQueryService.findById(productId))
                .build());
        log.info("memberId :{} productId :{} Liked 저장 시도", memberId, productId);
        viewService.addCountOne(productId);
    }

    public List<ProductResponse> getLikeHistoryList(
            LikeHistorySearchCondition condition,
            Long memberId) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize());
        log.info("memberId:{},  좋아하는 여부:{},size:{},page:{}", memberId, condition.getLiked(), condition.getSize(),
                condition.getPage());
        return productResponseRouter.getMatchList(condition.getLiked(), memberId, pageable);
    }
}
