package antigravity.repository;

import antigravity.entity.Customer;
import antigravity.entity.LikedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class LikedProductRpository {
    private final EntityManager em;

    public void findById(Long id) {
        Customer customer = em.find(Customer.class, id);
    }

    public List<LikedProduct> findByUserIdAndProductId(Long userId, Long productId) {
        String queryString = "SELECT lp FROM LikedProduct  lp " +
                " WHERE " +
                " lp.customer.id = :userId " +
                " AND lp.product.id = :productId  ";

        return em.createQuery(queryString, LikedProduct.class)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getResultList();
    }

    public Long countByProductId(Long id) {
        String sql = "select COUNT(lp) from LikedProduct lp where lp.product.id = :id";
        Long likedProductCount = em.createQuery(sql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return likedProductCount;
    }

    @Transactional
    public void save(LikedProduct likedProduct) {
        em.persist(likedProduct);
    }
}
