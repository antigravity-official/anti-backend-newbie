package antigravity.service;

import antigravity.entity.Member;
import antigravity.exception.NotFoundMemberException;
import antigravity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundMemberException());
    }
}
