package antigravity.infra;

import antigravity.domain.User;
import antigravity.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    @Query("select u from User u where u.id = :userId and u.deletedAt = null")
    Optional<User> findById(@Param("userId") Long userId);

}
