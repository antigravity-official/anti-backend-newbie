package antigravity.domain.repository;

import antigravity.domain.entity.Product;
import antigravity.domain.entity.User;
import antigravity.domain.entity.Want;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WantRepository extends JpaRepository<Want,Long> {
    Optional<Want> findByProductAndUser(Product product, User user);
}
