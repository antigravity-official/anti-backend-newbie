package antigravity.repository;

import antigravity.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class ProductRepository {
    private final EntityManager em;

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }
}
