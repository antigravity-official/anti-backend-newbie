package antigravity.user.repository;

import antigravity.user.application.UserRepository;
import antigravity.user.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    public FakeUserRepository() {
        initUser();
    }

    private void initUser() {
        LocalDateTime deletedAt = LocalDateTime.of(2023, 1, 11, 16, 52, 57);
        User deletedUser = User.builder()
                .id(1L)
                .email("user1@antigravity.kr")
                .name("회원1")
                .deletedAt(deletedAt)
                .build();
        users.add(deletedUser);

        for (int i = 2; i < 5; i++) {
            User user = User.builder()
                    .id((long) i)
                    .email("user" + i + "@antigravity.kr")
                    .name("회원" + i)
                    .build();
            users.add(user);
        }
    }

    @Override
    public User getById(Long userId) {
        return users.stream().filter(u -> u.getId().equals(userId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 고객을 찾을 수 없습니다."));
    }
}
