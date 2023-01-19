package antigravity.service;

import antigravity.entity.User;
import antigravity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 사용자존재확인() {
        User user1 = User.builder().id(1L).email("email@em.com").createdAt(LocalDateTime.now()).build();
        userRepository.save(user1);
        boolean result = userService.isExist(1L);
        System.out.println(result);
    }
}