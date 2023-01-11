package antigravity.user.application;

import antigravity.user.domain.User;

public interface UserRepository {

    User getById(Long userId);
}
