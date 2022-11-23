package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.ProductStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProductStatisticsRepository {
    private final EntityManager em;

    public ProductStatistics findByProductId(Long productId) {
        String sql = "select ps from ProductStatistics ps where ps.product.id = :productId ";
       return em.createQuery(sql, ProductStatistics.class)
                .setParameter("productId", productId)
                .getSingleResult();
    }

    public void save(ProductStatistics productStatistics) {
        em.persist(productStatistics);
    }
}
