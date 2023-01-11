package antigravity.user.service;

import antigravity.user.entity.User;

public interface UserService {
    User validateExistUser(Integer userId);
}
