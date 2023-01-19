package antigravity.service;

import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchUserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User searchUserByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.NOT_FOUND_USER);
                });

        if (user.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.NOT_EXISTS_USER);
        }

        return user;
    }
}
