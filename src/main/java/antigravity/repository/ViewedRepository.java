package antigravity.repository;

import antigravity.entity.Viewed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedRepository extends JpaRepository<Viewed, Long> {
}
