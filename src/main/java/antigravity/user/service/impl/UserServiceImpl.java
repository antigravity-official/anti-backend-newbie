package antigravity.user.service.impl;

import antigravity.global.exception.AntiException;
import antigravity.user.entity.User;
import antigravity.user.exception.UserErrorCode;
import antigravity.user.repository.UserRepository;
import antigravity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User validateExistUser(Integer userId) {
        return userRepository.findById((long)userId).orElseThrow(() -> new AntiException(UserErrorCode.USER_NOT_EXIST));
    }
}
