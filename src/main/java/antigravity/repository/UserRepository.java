package antigravity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByIdAndDeletedAtIsNull(Long userId);
}
