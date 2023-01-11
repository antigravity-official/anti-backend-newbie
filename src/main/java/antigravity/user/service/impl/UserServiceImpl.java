package antigravity.user.service.impl;

import antigravity.user.repository.UserRepository;
import antigravity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void validateExistUser(Integer userId) {
        userRepository.findById((long)userId);
    }
}
