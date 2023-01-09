package antigravity.service;

import org.springframework.stereotype.Service;

import antigravity.entity.User;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public User findUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("User Not Found"));

		if (user.isDeleted()) {
			throw new IllegalStateException("Already User Deleted");
		}

		return user;
	}

}
