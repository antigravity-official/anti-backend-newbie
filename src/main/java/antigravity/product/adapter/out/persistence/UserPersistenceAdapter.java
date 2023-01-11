package antigravity.product.adapter.out.persistence;

import antigravity.product.application.port.out.QueryUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class UserPersistenceAdapter implements QueryUserPort {

	private final UserRepository userRepository;

	@Override
	public boolean existUser(Long userId) {
		return userRepository.existsByIdAndDeletedAtIsNull(userId);
	}
}
