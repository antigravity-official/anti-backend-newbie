package antigravity.repository;

import antigravity.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("찾는 유저가 없을 때 예외를 발생시킨다.")
    @Test
    public void returnProductError() {
        Long id = 100L;
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(id).get();
        });
    }
}
