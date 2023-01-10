package antigravity.service;

import antigravity.repository.LikeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeHistoryQueryService {
    private final LikeHistoryRepository likeHistoryRepository;

    public boolean exists(Long memberId, Long productId) {
        return likeHistoryRepository.existsByMemberIdAndProductId(memberId, productId);
    }
}
