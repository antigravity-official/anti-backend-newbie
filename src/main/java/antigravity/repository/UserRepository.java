package antigravity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
