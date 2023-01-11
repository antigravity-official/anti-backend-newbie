package antigravity.user.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import antigravity.user.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    private UserService sut;

    @BeforeEach
    void beforeEach() {
        UserRepository userRepository = new FakeUserRepository();
        sut = new UserService(userRepository);
    }

    @Test
    @DisplayName("유효한 고객인지 확인할 수 있다.")
    void whenCheckUserIsActive_thenSuccess() {
        //given
        Long userId = 2L;

        //expected
        sut.checkUserIsActive(userId);
    }

    @Test
    @DisplayName("존재하지 않는 고객 ID는 예외를 던진다.")
    void givenNotFoundUserByUserId_whenCheckUserIsActive_thenExceptionShouldBeThrown() {
        //given
        Long userId = 999L;

        //expected
        assertThatThrownBy(() -> sut.checkUserIsActive(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 고객을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("삭제된 고객은 유효한 고객이 아니므로 예외를 던진다.")
    void givenDeletedUserId_whenCheckUserIsActive_thenExceptionShouldBeThrown() {
        //given
        Long userId = 1L;

        //expected
        assertThatThrownBy(() -> sut.checkUserIsActive(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제된 고객입니다.");
    }
}