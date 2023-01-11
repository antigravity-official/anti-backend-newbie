package antigravity.user.application;

import antigravity.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void checkUserIsActive(Long userId) {
        User user = userRepository.getById(userId);
        if (user.isDeleted()) {
            throw new IllegalArgumentException("삭제된 고객입니다.");
        }
    }

}
