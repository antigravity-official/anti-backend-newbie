package antigravity.service;

import antigravity.entity.LikeHistory;
import antigravity.exception.ExistLikeHistoryException;
import antigravity.repository.LikeHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeHistoryService {
    private final LikeHistoryQueryService likeHistoryQueryService;

    private final MemberQueryService memberQueryService;
    private final ProductQueryService productQueryService;
    private final LikeHistoryRepository likeHistoryRepository;

    public void addNotDuplicatedLikeHistory(Long productId, Long memberId) {
        if (likeHistoryQueryService.exists(memberId, productId)) {
            throw new ExistLikeHistoryException();
        }
        likeHistoryRepository.save(LikeHistory.builder()
                .member(memberQueryService.findById(memberId))
                .product(productQueryService.findById(productId))
                .build());
        log.info("memberId :{} productId :{} Liked 저장 시도", memberId, productId);
    }

}
