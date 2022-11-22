package antigravity.repository;

import antigravity.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
