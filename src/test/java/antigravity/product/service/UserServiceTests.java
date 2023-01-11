package antigravity.product.service;

import antigravity.global.exception.AntiException;
import antigravity.user.repository.UserRepository;
import antigravity.user.service.UserService;
import antigravity.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Test
    @DisplayName("유저가 존재하지 않을 경우 400 에러를 발생시킨다.")
    void notExistUser() {
        AntiException exception = assertThrows(AntiException.class, () -> {
            userService.validateExistUser(3);
        });
        assertEquals("해당 아이디를 가진 유저가 존재하지 않습니다.",exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

}
