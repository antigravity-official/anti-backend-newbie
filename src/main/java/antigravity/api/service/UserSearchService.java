package antigravity.api.service;

import antigravity.api.repository.UserRepository;
import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User searchUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }
}
