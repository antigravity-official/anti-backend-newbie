package antigravity.service;

import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void getUser(Long userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomException("존재하지 않는 유저 ID 입니다"));
        System.out.println(userEntity);

    }
}
