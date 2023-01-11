package antigravity.user.repository;

import antigravity.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    User user;
    @BeforeEach
    void settings() {
        user = User.builder()
                .name("조소연")
                .createdAt(LocalDateTime.now())
                .build();
    }
    @Test
    @DisplayName("유저정보 저장 후 찾기")
    void saveAndFind() {
        //when
        Long id = userRepository.save(user);
        User findUser = userRepository.findById(id);

        //then
        assertEquals(id, findUser.getId());
    }

}
