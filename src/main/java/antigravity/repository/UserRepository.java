package antigravity.repository;

import antigravity.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }
}
