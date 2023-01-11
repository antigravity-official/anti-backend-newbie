package antigravity.product.adapter.out.persistence;

import antigravity.product.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User,Long> {

	boolean existsByIdAndDeletedAtIsNull(Long userId);
}
