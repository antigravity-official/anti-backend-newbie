package antigravity.service;

import antigravity.exception.NotFoundMemberException;
import antigravity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public Long validateId(String userId) {
        Long id = Long.parseLong(userId);
        if (!memberRepository.existsById(id)) {
            throw new NotFoundMemberException();
        }
        return id;
    }
}
