package antigravity.repository;

import antigravity.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void postUser() {
        //given
        User user = new User();
        user.setEmail("user5@antigravity.kr");
        user.setName("회원5");

        //when
        User saveUser = userRepository.save(user);
        Long savedId = saveUser.getId();
        Optional<User> findMember = userRepository.findById(savedId);

        //then
        Assertions.assertThat(findMember.get().getId()).isEqualTo(user.getId());
        Assertions.assertThat(findMember.get().getName()).isEqualTo(user.getName());
    }

}