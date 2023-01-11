package antigravity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.User;
import antigravity.exception.AntigravityException;
import antigravity.exception.ErrorCode;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User findUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new AntigravityException(ErrorCode.USER_ID_NOT_FOUND,
				String.format("%d not founded", userId)));

		if (user.isDeleted()) {
			throw new AntigravityException(ErrorCode.ALREADY_DELETED_USER,
				String.format("%d already deleted", userId));
		}

		return user;
	}

}
