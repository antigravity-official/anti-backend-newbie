package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.Users;
import antigravity.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish,Long> {
    Optional<Wish> findByProductAndUsers(Product product, Users users);
}
