package antigravity.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import antigravity.config.JpaConfig;
import antigravity.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class UserRepositoryImplTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("userId로 User를 찾을 수 있다.")
    void givenUserId_whenFindById_thenOptionalHasUser() {
        //given
        Long userId = 1L;

        //when
        UserRepositoryImpl sut = new UserRepositoryImpl(userJpaRepository);
        User user = sut.getById(userId);

        //then
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("userId로 User를 찾지 못하면 예외를 반환한다.")
    void givenNotFoundUserByUserId_whenFindById_thenExceptionShouldBeThrown() {
        //given
        Long userId = 0L;

        //when
        UserRepositoryImpl sut = new UserRepositoryImpl(userJpaRepository);

        //then
        assertThatThrownBy(() -> sut.getById(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 고객을 찾을 수 없습니다.");
    }
}