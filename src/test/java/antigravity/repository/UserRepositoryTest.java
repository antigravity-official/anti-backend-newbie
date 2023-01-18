package antigravity.repository;

import antigravity.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("user 잘 찾는 지 확인")
    @Test
    public void findByIdTest() {
        Long id = 1L;
        User user = userRepository.findById(id).get();
        Assertions.assertNotNull(user);
    }

}