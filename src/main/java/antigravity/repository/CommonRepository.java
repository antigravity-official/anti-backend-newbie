package antigravity.repository;

import java.util.Optional;

public interface CommonRepository<T> {

    Optional<T> findById(Long id);
}
